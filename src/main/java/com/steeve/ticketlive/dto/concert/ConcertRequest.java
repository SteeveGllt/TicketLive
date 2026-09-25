package com.steeve.ticketlive.dto.concert;

import lombok.Data;

import java.util.Date;

@Data
public class ConcertRequest {
    private String nomArtiste;
    private String titreEvenement;
    private Date dateConcert;
    private String lieu;
    private String ville;
    private String description;
    private String imageUrl;
}
