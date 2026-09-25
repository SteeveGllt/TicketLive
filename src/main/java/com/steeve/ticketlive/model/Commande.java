package com.steeve.ticketlive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_commande")
    private LocalDateTime dateCommande;

    private String statut;

    @Column(name = "montantTotal")
    private BigDecimal montantTotal;

    @Column(name = "stripe_payment_id")
    private String stripePaymentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LigneCommande> lignesCommande = new ArrayList<>();
}
