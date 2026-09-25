package com.steeve.ticketlive.service;

import com.steeve.ticketlive.dto.stripe.SessionPaimentResponse;
import com.steeve.ticketlive.model.Billet;
import com.steeve.ticketlive.model.Commande;
import com.steeve.ticketlive.model.LigneCommande;
import com.steeve.ticketlive.repository.BilletRepository;
import com.steeve.ticketlive.repository.CommandeRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaiementService {
    private final CommandeRepository commandeRepository;
    private final BilletRepository billetRepository;

    public SessionPaimentResponse createPaiementSession(Long commandeId, Long userId) throws StripeException {
        Commande commande = commandeRepository.findById(commandeId).orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if(!commande.getUser().getId().equals(userId)){
            throw new RuntimeException("Cette commande ne vous appartient pas");
        }
        if (!"EN_ATTENTE".equals(commande.getStatut())){
            throw new RuntimeException("Cette commande a déjà été traitée");
        }

        List<SessionCreateParams.LineItem> lineItems = commande.getLignesCommande().stream()
                .map(this::toLineItem)
                .toList();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/paiement-succes?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:3000/paiement-annule")
                .putMetadata("commandeId", commande.getId().toString())
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);

        SessionPaimentResponse response = new SessionPaimentResponse();
        response.setCheckoutUrl(session.getUrl());
        return response;

    }

    private SessionCreateParams.LineItem toLineItem(LigneCommande ligne) {
        long montantEnCentimes = ligne.getPrixUnitaire()
                .multiply(BigDecimal.valueOf(100))
                .longValue();

        return SessionCreateParams.LineItem.builder()
                .setQuantity((long) ligne.getQuantite())
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("eur")
                                .setUnitAmount(montantEnCentimes)
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName(ligne.getCategoriePlace().getNom())
                                                .build())
                                .build())
                .build();
    }

    public void confirmerPaiement(Long commandeId){
        Commande commande = commandeRepository.findById(commandeId).orElseThrow(() -> new RuntimeException("Commande introuvable"));

        if ("PAYEE".equals(commande.getStatut())){
            return;
        }
        commande.setStatut("PAYEE");
        commandeRepository.save(commande);

        for (LigneCommande ligne : commande.getLignesCommande()){
            for (int i = 0; i < ligne.getQuantite(); i++){
                Billet billet = new Billet();
                billet.setLigneCommande(ligne);
                billet.setCodeUnique(UUID.randomUUID().toString());
                billet.setStatut("VALIDE");
                billetRepository.save(billet);
            }
        }
    }
}
