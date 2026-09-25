package com.steeve.ticketlive.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "categories_places")
public class CategoriePlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private BigDecimal prix;

    private Integer quantiteTotale;

    private Integer quantiteDisponible;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;
}
