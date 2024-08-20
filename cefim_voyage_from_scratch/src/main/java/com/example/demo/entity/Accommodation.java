package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor                              // constructeur sans paramètres
@Entity                                         // présise que l'entité représente une table en base de données
@Table(name = "accommodation")                  // nom de la table
public class Accommodation {
    @Id                                         // identifiant de l'entité unique
    @GeneratedValue (strategy = IDENTITY)       // auto-increment
    private Long id;
    @Column(name = "name")                      // nom de la colonne en cas de personnalisation
    private String name;
    private String address;
    private String phone;
    private int price;
    private LocalDateTime start;
    private LocalDateTime end;

    //relation entre un logement avec un voyage
    @ManyToOne(fetch = LAZY)                                 // plusieurs logements peuvent être associés à un voyage et un logement est un item de la table Accommodation
    @JoinColumn(name = "travel_id")
    private Travel travel;


}
