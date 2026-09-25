package com.steeve.ticketlive.controller;

import com.steeve.ticketlive.dto.commande.CommandeRequest;
import com.steeve.ticketlive.dto.commande.CommandeResponse;
import com.steeve.ticketlive.dto.security.services.UserDetailsImpl;
import com.steeve.ticketlive.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {
    private final CommandeService commandeService;

    @PostMapping
    public ResponseEntity<CommandeResponse> save(@RequestBody CommandeRequest commandeRequest, @AuthenticationPrincipal UserDetailsImpl userConnected){
        CommandeResponse commandeResponse = commandeService.saveCommande(commandeRequest, userConnected.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(commandeResponse);
    }
}
