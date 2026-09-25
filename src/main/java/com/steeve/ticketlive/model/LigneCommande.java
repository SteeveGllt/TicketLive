package com.steeve.ticketlive.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "lignes_commande")
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantite;

    @Column(name = "prix_unitaire")
    private BigDecimal prixUnitaire;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "categorie_place_id")
    private CategoriePlace categoriePlace;
}
