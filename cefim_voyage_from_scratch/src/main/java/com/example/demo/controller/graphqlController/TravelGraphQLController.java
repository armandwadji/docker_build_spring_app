package com.example.demo.controller.graphqlController;

import com.example.demo.dto.TravelDTO;
import com.example.demo.exception.TravelException;
import com.example.demo.service.TravelService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller @AllArgsConstructor
public class TravelGraphQLController {

    private TravelService travelService;

    @QueryMapping
    public List<TravelDTO> travels(){return travelService.list();}

    @QueryMapping
    public TravelDTO travel(@Argument int id) throws TravelException {return travelService.read(id);}

    @MutationMapping
    public TravelDTO createTravel(@Argument TravelDTO travel){
        return travelService.create(travel);
    }

    @MutationMapping
    public TravelDTO updateTravel(@Argument int id, @Argument TravelDTO travel) throws TravelException {return travelService.update(id, travel);}

    @MutationMapping
    public void deleteTravel(@Argument int id) throws TravelException { travelService.delete(id);}


}
