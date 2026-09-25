package com.steeve.ticketlive.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "billets")
public class Billet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code_unique")
    private String codeUnique;

    private String statut;

    @Column(name = "date_generation")
    private Date dateGeneration;

    @ManyToOne
    @JoinColumn(name = "ligne_commande_id")
    private LigneCommande ligneCommande;

}
