package com.example.demo.repository;


//c'est ici qu'on fait nos appels  à la base de données


import com.example.demo.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccommodationRepository extends JpaRepository <Accommodation, Integer> {



}
