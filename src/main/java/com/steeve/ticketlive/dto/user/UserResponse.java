package com.steeve.ticketlive.dto.user;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
}
