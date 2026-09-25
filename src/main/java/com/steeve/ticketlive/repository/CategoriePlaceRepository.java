package com.steeve.ticketlive.repository;

import com.steeve.ticketlive.model.CategoriePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriePlaceRepository extends JpaRepository<CategoriePlace, Long> {
}
