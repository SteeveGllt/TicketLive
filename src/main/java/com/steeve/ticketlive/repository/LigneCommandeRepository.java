package com.steeve.ticketlive.repository;

import com.steeve.ticketlive.model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
}
