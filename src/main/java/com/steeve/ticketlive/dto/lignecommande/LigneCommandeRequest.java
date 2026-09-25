package com.steeve.ticketlive.dto.lignecommande;

import lombok.Data;

@Data
public class LigneCommandeRequest {
    private Long categoriePlaceId;
    private Integer quantite;
}
