package com.example.demo.controller.advice;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//interface qui sera de contrat pour les crud de TOUT les controller de l'app

@RestController //Ceci est un controller qui ca faire des CRUD, dans un protocole HTTP, comportement sera GET, POST etc...
@RequestMapping(produces = APPLICATION_JSON_VALUE) //On précise le format qui sera produit, ici, du JSON
public interface CrudControllerAdviceInterface<T> {

    @ResponseStatus(value = CREATED) //reverra, apres la cration le statut HTTP 201
    @PostMapping(consumes = APPLICATION_JSON_VALUE) //précise le format de ce qui sera consommé/envoyé
    //méthode qui créé un objet de type T
    T create (@Valid @RequestBody T object);

    @ResponseStatus(value = OK) //Réponse ok pour poursuivre...
    @GetMapping //Parce qu'on veut un GET pour avoir la liste
    @ResponseBody List<T> reads (); //le constenu de la réponse sera la lste d'objet T

    @ResponseStatus(value = OK) //Réponse ok pour poursuivre...
    @GetMapping(path = "{id}") //Parce qu'on veut un GET pour avoir la liste //on précise comment aller récupéer la variable qu'on va utiliser avec PathVaraible
    @ResponseBody T read(@PathVariable int id) throws Exception ; //REQUEST BODY :: le constenu de la réponse sera un objet de type T

    @ResponseStatus(ACCEPTED) //Réponse "acceptée" en cas de mise à jour
    @PutMapping(path = "{id}", consumes = APPLICATION_JSON_VALUE) //On va faire un PUT, via la varible id, la donnée d'entrée est en JSON
    T update(@PathVariable int id, @Valid @RequestBody T object) throws Exception; //REQUEST BODY :: le constenu de la réponse sera un objet de type T

    @ResponseStatus(NO_CONTENT) //Réponse "OK, rien"
    @DeleteMapping(path = "{id}") //On va faire une DELETE
    void delete(@PathVariable int id) throws Exception;

}
