package com.example.demo.controller;


import com.example.demo.controller.advice.CrudControllerAdviceInterface;
import com.example.demo.dto.ActivityDTO;
import com.example.demo.dto.ActivityDetailDTO;
import com.example.demo.exception.ActivityException;
import com.example.demo.service.ActivityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path = "activities")
@RestController
public class ActivityController implements CrudControllerAdviceInterface<ActivityDTO> {
    private ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public ActivityDTO create(ActivityDTO activityDTO) {
        return activityService.create(activityDTO);
    }

    @Override
    public List<ActivityDTO> reads() {
        return activityService.list();
    }

    @Override
    public ActivityDetailDTO read(int id) throws Exception {
        return activityService.read(id);
    }

    @Override
    public ActivityDTO update(int id, ActivityDTO activityDTO) throws ActivityException {
        return activityService.update(id, activityDTO);
    }

    @Override
    public void delete(int id) throws ActivityException {
        activityService.delete(id);
    }
}
