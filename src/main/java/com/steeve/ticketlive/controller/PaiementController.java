package com.steeve.ticketlive.controller;

import com.steeve.ticketlive.dto.security.services.UserDetailsImpl;
import com.steeve.ticketlive.dto.stripe.SessionPaimentResponse;
import com.steeve.ticketlive.service.PaiementService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping("/{id}/paiement")
    public ResponseEntity<SessionPaimentResponse> creerPaiement(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl utilisateurConnecte) throws StripeException {

        SessionPaimentResponse response = paiementService.createPaiementSession(id, utilisateurConnecte.getId());
        return ResponseEntity.ok(response);
    }
}
