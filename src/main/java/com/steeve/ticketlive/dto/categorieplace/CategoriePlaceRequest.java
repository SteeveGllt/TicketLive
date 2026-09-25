package com.steeve.ticketlive.dto.categorieplace;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoriePlaceRequest {
    private Long concertId;
    private String nom;
    private BigDecimal prix;
    private Integer quantiteTotale;
}
