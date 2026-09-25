package com.steeve.ticketlive.dto.categorieplace;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoriePlaceResponse {
    private Long id;
    private String nom;
    private BigDecimal prix;
    private Integer quantiteTotale;
    private Integer quantiteDisponible;
    private String nomConcert;
}
