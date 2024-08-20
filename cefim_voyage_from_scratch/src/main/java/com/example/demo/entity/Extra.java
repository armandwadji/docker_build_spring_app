package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import static jakarta.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor                              // constructeur sans paramètres
@Entity                                         // présise que l'entité représente une table en base de données
@Table(name = "extra")                          // nom de la table
public class Extra {
    @Id                                         // identifiant de l'entité unique
    @GeneratedValue(strategy = IDENTITY)       // auto-increment
    private Long id;
    @Column(name = "name")                      // nom de la colonne en cas de personnalisation
    private String name;
    private int price;
    private LocalDateTime createdat;

    //relation entre un ou des extras avec un voyage
    @ManyToOne(fetch = FetchType.LAZY)                                 // plusieurs extras peuvent être associés à un voyage et un extra est un item de la table Extra
    @JoinColumn(name = "travel_id")
    private Travel travel;

}