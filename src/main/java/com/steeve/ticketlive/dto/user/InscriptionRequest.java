package com.steeve.ticketlive.dto.user;

import lombok.Data;

@Data
public class InscriptionRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
}
