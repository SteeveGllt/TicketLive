package com.steeve.ticketlive.dto.commande;

import com.steeve.ticketlive.dto.lignecommande.LigneCommandeResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandeResponse {
    private Long id;
    private LocalDateTime dateCommande;
    private String statut;
    private BigDecimal montantTotal;
    private List<LigneCommandeResponse> lignes;
}
