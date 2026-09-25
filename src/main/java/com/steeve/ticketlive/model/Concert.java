package com.steeve.ticketlive.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "concerts")
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_artiste")
    private String nomArtiste;

    @Column(name = "titre_evenement")
    private String titreEvenement;

    @Column(name = "date_concert")
    private Date dateConcert;

    private String lieu;

    private String ville;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "date_creation")
    private Date dateCreation;
}
