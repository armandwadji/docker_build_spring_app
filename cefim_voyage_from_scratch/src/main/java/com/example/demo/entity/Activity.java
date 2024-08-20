package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private int price;
    private String image;

    //Relation entre une activité et le contact à joindre pour réaliser l'activité (ex : loueur de quad)
    @OneToMany (fetch = FetchType.EAGER, mappedBy = "activity")
    private List<Contact> contact = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;
}
