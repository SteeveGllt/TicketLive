package com.steeve.ticketlive.repository;

import com.steeve.ticketlive.model.Billet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {
}
