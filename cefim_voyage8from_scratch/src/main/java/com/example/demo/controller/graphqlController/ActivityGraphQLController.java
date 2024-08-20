package com.example.demo.controller.graphqlController;

import com.example.demo.dto.ActivityDTO;
import com.example.demo.entity.Activity;
import com.example.demo.exception.ActivityException;
import com.example.demo.exception.TravelException;
import com.example.demo.service.graphqlService.ActivityGraphQLService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller @AllArgsConstructor
public class ActivityGraphQLController {

    private ActivityGraphQLService activityGraphQLService;


    @QueryMapping
    public List<Activity> activities(){return activityGraphQLService.activities();}

    @QueryMapping
    public Activity activity(@Argument int id) throws ActivityException {return activityGraphQLService.activity(id);}

    @MutationMapping
    public Activity createActivity(@Argument int travelId, @Argument ActivityDTO activity) throws TravelException {return activityGraphQLService.create(travelId, activity);}

    @MutationMapping
    public Activity updateActivity(@Argument int id, @Argument ActivityDTO activity) throws ActivityException {return activityGraphQLService.update(id, activity);}

    @MutationMapping
    public String deleteActivity(@Argument int id) throws ActivityException {return activityGraphQLService.delete(id);}
}
