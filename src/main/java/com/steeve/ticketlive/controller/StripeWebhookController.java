package com.steeve.ticketlive.controller;

import com.steeve.ticketlive.service.PaiementService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/webhook")
public class StripeWebhookController {
    @Value("{stripe.webhook.secret}")
    private String webhookSecret;

    private final PaiementService paiementService;

    @PostMapping("/stripe")
    public ResponseEntity<String> gererWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader){
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e){
            return ResponseEntity.badRequest().body("Signature invalide");
        }

        if ("checkout.session.complete".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElseThrow(() -> new RuntimeException("Impossible de lire la session"));

            Long commandeId = Long.valueOf(session.getMetadata().get("commandeId"));
            paiementService.confirmerPaiement(commandeId);
        }
        return ResponseEntity.ok("Reçu");
    }
}
