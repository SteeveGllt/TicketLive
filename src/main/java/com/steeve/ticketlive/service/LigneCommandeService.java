package com.steeve.ticketlive.service;

import com.steeve.ticketlive.dto.lignecommande.LigneCommandeRequest;
import com.steeve.ticketlive.dto.lignecommande.LigneCommandeResponse;
import com.steeve.ticketlive.model.CategoriePlace;
import com.steeve.ticketlive.model.Commande;
import com.steeve.ticketlive.model.LigneCommande;
import com.steeve.ticketlive.repository.CategoriePlaceRepository;
import com.steeve.ticketlive.repository.CommandeRepository;
import com.steeve.ticketlive.repository.LigneCommandeRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class LigneCommandeService {
    private final CommandeRepository commandeRepository;
    private final CategoriePlaceRepository categoriePlaceRepository;
    private final LigneCommandeRepository ligneCommandeRepository;


}
