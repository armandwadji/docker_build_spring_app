package com.example.demo.service.graphqlService;

import com.example.demo.dto.ActivityDTO;
import com.example.demo.entity.Activity;
import com.example.demo.entity.Travel;
import com.example.demo.exception.ActivityException;
import com.example.demo.exception.TravelException;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.repository.TravelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service @AllArgsConstructor
public class ActivityGraphQLService {

    public static final String ACTIVITY_NOT_FOUND = "Activity Not Found";
    private ActivityRepository activityRepository;
    private TravelRepository travelRepository;

    public List<Activity> activities(){return activityRepository.findAll();}

    public Activity activity(int id) throws ActivityException {return activityRepository.findById(id).orElseThrow(() -> new ActivityException(ACTIVITY_NOT_FOUND, NOT_FOUND));}

    public Activity create(int travelId, ActivityDTO activityDTO) throws TravelException {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new TravelException("Travel Not Found", NOT_FOUND));

        Activity activity = Activity.builder()
                .title(activityDTO.getTitle())
                .description(activityDTO.getDescription())
                .start(activityDTO.getStart())
                .end(activityDTO.getEnd())
                .travel(travel)
                .build();

        return activityRepository.save(activity);

    }

    public Activity update(int id, ActivityDTO activityDTO) throws ActivityException {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new ActivityException(ACTIVITY_NOT_FOUND, NOT_FOUND));
        activity.setTitle(activityDTO.getTitle());
        activity.setDescription(activityDTO.getDescription());
        activity.setStart(activityDTO.getStart());
        activity.setEnd(activityDTO.getEnd());

        return activityRepository.save(activity);
    }

    public String delete(int id) throws ActivityException {
        Activity activity = activityRepository.findById(id).orElseThrow(() -> new ActivityException(ACTIVITY_NOT_FOUND, NOT_FOUND));
        activityRepository.delete(activity);

        return "Activity delete Successfully";
    }
}


