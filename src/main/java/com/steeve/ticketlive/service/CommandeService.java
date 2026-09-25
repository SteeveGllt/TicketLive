package com.steeve.ticketlive.service;

import com.steeve.ticketlive.dto.commande.CommandeRequest;
import com.steeve.ticketlive.dto.commande.CommandeResponse;
import com.steeve.ticketlive.dto.lignecommande.LigneCommandeRequest;
import com.steeve.ticketlive.dto.lignecommande.LigneCommandeResponse;
import com.steeve.ticketlive.model.CategoriePlace;
import com.steeve.ticketlive.model.Commande;
import com.steeve.ticketlive.model.LigneCommande;
import com.steeve.ticketlive.model.User;
import com.steeve.ticketlive.repository.CategoriePlaceRepository;
import com.steeve.ticketlive.repository.CommandeRepository;
import com.steeve.ticketlive.repository.LigneCommandeRepository;
import com.steeve.ticketlive.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeService {
    private final UserRepository userRepository;
    private final CommandeRepository commandeRepository;
    private final CategoriePlaceRepository categoriePlaceRepository;
    private final LigneCommandeRepository ligneCommandeRepository;

    @Transactional
    public CommandeResponse saveCommande(@RequestBody CommandeRequest request, Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User introuvable"));

        Commande commande = new Commande();
        commande.setUser(user);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut("EN_ATTENTE");

        List<LigneCommande> ligneCommandes = new ArrayList<>();
        BigDecimal montantTotal = BigDecimal.ZERO;

        for (LigneCommandeRequest ligneRequest : request.getLignes()){
            CategoriePlace categoriePlace = categoriePlaceRepository.findById(ligneRequest.getCategoriePlaceId())
                    .orElseThrow(() -> new RuntimeException("Catégorie place introuvable"));

            if (categoriePlace.getQuantiteDisponible() < ligneRequest.getQuantite()){
                throw new RuntimeException("Stock insuffisant");
            }

            BigDecimal sousTotal = categoriePlace.getPrix().multiply(BigDecimal.valueOf(ligneRequest.getQuantite()));
            montantTotal = montantTotal.add(sousTotal);

            categoriePlace.setQuantiteDisponible(categoriePlace.getQuantiteDisponible() - ligneRequest.getQuantite());
            categoriePlaceRepository.save(categoriePlace);

            LigneCommande ligneCommande = new LigneCommande();
            ligneCommande.setCategoriePlace(categoriePlace);
            ligneCommande.setQuantite(ligneRequest.getQuantite());
            ligneCommande.setPrixUnitaire(categoriePlace.getPrix());
            ligneCommandes.add(ligneCommande);
        }
        commande.setMontantTotal(montantTotal);
        Commande commandeSauvegarde = commandeRepository.save(commande);

        for (LigneCommande ligne : ligneCommandes){
            ligne.setCommande(commandeSauvegarde);
            ligneCommandeRepository.save(ligne);
        }
        return toResponse(commandeSauvegarde, ligneCommandes);
    }

    private CommandeResponse toResponse(Commande commande, List<LigneCommande> ligneCommandes){
        CommandeResponse commandeResponse = new CommandeResponse();
        commandeResponse.setId(commande.getId());
        commandeResponse.setDateCommande(commande.getDateCommande());
        commandeResponse.setMontantTotal(commande.getMontantTotal());
        commandeResponse.setStatut(commande.getStatut());
        List<LigneCommandeResponse> ligneCommandeResponses = ligneCommandes.stream()
                        .map(this::toLigneResponse)
                                .toList();
        commandeResponse.setLignes(ligneCommandeResponses);

        return commandeResponse;
    }

    private LigneCommandeResponse toLigneResponse(LigneCommande ligneCommande){
        LigneCommandeResponse ligneCommandeResponse = new LigneCommandeResponse();
        ligneCommandeResponse.setNomCategoriePlace(ligneCommande.getCategoriePlace().getNom());
        ligneCommandeResponse.setPrixUnitaire(ligneCommande.getPrixUnitaire());
        ligneCommandeResponse.setQuantite(ligneCommande.getQuantite());

        return ligneCommandeResponse;
    }
}
