package com.steeve.ticketlive.dto.lignecommande;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LigneCommandeResponse {
    private String nomCategoriePlace;
    private Integer quantite;
    private BigDecimal prixUnitaire;
}
