package com.example.demo.controller.graphqlController;

import com.example.demo.dto.ExtraDTO;
import com.example.demo.entity.Extra;
import com.example.demo.exception.ExtraException;
import com.example.demo.exception.TravelException;
import com.example.demo.service.graphqlService.ExtraGraphQLService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller @AllArgsConstructor
public class ExtraGraphQLController {

    private ExtraGraphQLService extraGraphQLService;

    @QueryMapping
    public List<Extra> extras(){return extraGraphQLService.extras();}

    @QueryMapping
    public Extra extra(@Argument int id) throws ExtraException {return extraGraphQLService.extra(id);}

    @MutationMapping
    public Extra createExtra(@Argument int travelId, @Argument ExtraDTO extra) throws TravelException {return extraGraphQLService.create(travelId, extra);}

    @MutationMapping
    public Extra updateExtra(@Argument int id, @Argument ExtraDTO extra) throws ExtraException {return extraGraphQLService.update(id, extra);}

    @MutationMapping
    public String deleteExtra(@Argument int id) throws ExtraException {return extraGraphQLService.delete(id);}
}
