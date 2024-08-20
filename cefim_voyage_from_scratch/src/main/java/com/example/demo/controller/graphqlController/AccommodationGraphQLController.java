package com.example.demo.controller.graphqlController;

import com.example.demo.dto.AccommodationDTO;
import com.example.demo.entity.Accommodation;
import com.example.demo.exception.AccommodationException;
import com.example.demo.exception.TravelException;
import com.example.demo.service.graphqlService.AccommodationGraphQLService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller @AllArgsConstructor
public class AccommodationGraphQLController {

    private final AccommodationGraphQLService accommodationGraphQLService;

    @QueryMapping
    public List<Accommodation> accommodations(){return accommodationGraphQLService.accommodations();}

    @QueryMapping
    public Accommodation accommodation(@Argument int id) throws AccommodationException {return accommodationGraphQLService.accommodation(id);}

    @MutationMapping
    public Accommodation createAccommodation(@Argument int travelId, @Argument AccommodationDTO accommodation) throws TravelException {return accommodationGraphQLService.create(travelId, accommodation);}

    @MutationMapping
    public Accommodation updateAccommodation(@Argument int id, @Argument AccommodationDTO accommodation) throws AccommodationException {return accommodationGraphQLService.update(id, accommodation);}

    @MutationMapping
    public String deleteAccommodation(@Argument int id) throws AccommodationException {return accommodationGraphQLService.delete(id);}
}
