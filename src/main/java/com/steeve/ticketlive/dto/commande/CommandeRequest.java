package com.steeve.ticketlive.dto.commande;

import com.steeve.ticketlive.dto.lignecommande.LigneCommandeRequest;
import lombok.Data;

import java.util.List;

@Data
public class CommandeRequest {
    private List<LigneCommandeRequest> lignes;
}
