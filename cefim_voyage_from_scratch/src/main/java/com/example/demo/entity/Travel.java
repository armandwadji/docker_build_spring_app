package com.example.demo.entity;

import lombok.*;
import java.time.LocalDate;
import jakarta.persistence.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.TemporalType.DATE;

@Entity
@Builder
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Table(name = "travel")
public class Travel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "start")
    @Temporal(DATE)
    private LocalDate start;

    @Column(name = "end")
    @Temporal(DATE)
    private LocalDate end;
}
