package com.example.demo.controller.graphqlController;

import com.example.demo.dto.ContactDTO;
import com.example.demo.entity.Contact;
import com.example.demo.exception.ActivityException;
import com.example.demo.exception.ContactException;
import com.example.demo.service.graphqlService.ContactGraphQLService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller @AllArgsConstructor
public class ContactGraphQLController {

    private ContactGraphQLService contactGraphQLService;

    @QueryMapping
    public Contact contact(@Argument int id) throws ContactException {return contactGraphQLService.contact(id);}

    @MutationMapping
    public Contact createContact(@Argument int activityId, @Argument ContactDTO contact) throws ActivityException {return contactGraphQLService.create(activityId, contact);}

    @MutationMapping
    public Contact updateContact(@Argument int id, @Argument ContactDTO contact) throws ContactException {return contactGraphQLService.update(id, contact);}

    @MutationMapping
    public String deleteContact(@Argument int id) throws ContactException {return contactGraphQLService.delete(id);}
}
