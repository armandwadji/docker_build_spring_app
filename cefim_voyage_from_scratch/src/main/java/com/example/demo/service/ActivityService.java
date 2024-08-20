package com.example.demo.service;

import com.example.demo.dto.ActivityDTO;
import com.example.demo.dto.ActivityDetailDTO;
import com.example.demo.entity.Activity;
import com.example.demo.exception.ActivityException;
import com.example.demo.mapper.ActivityDTOMapper;
import com.example.demo.repository.ActivityRepository;
import com.example.demo.service.interfaces.CrudServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class ActivityService implements CrudServiceInterface<ActivityDTO> {
    private ActivityRepository activityRepository;
    private ActivityDTOMapper activityDTOMapper;

    @Override
    public ActivityDTO create(ActivityDTO activityDTO) {

        //création de l'activité à partir d'une activité DTO
        Activity activity = activityDTOMapper.toEntity(activityDTO);

        //save en base
        Activity activitySave = activityRepository.save(activity);

        //réponse pour le front (donc de type DTO)
        return activityDTOMapper.toDTO(activitySave);
    }

    @Override
    public List<ActivityDTO> list() {
        return activityDTOMapper.toDTOList(activityRepository.findAll());
    }

    @Override
    public ActivityDetailDTO read(int id) throws ActivityException {

        return activityRepository.findById(id)
                .map(activityDTOMapper::toDetailDTO)
                .orElseThrow(() -> new ActivityException("Activity not found", NOT_FOUND));
    }

    @Override
    public ActivityDTO update(int id, ActivityDTO activityDTO) throws ActivityException {
        Activity activityInBdd = activityRepository.findById(id)
                .orElseThrow(() -> new ActivityException("Activity not found", NOT_FOUND));

        activityInBdd.setTitle(activityDTO.getTitle());
        activityInBdd.setDescription(activityDTO.getDescription());
        activityInBdd.setStart(activityDTO.getStart());
        activityInBdd.setEnd(activityDTO.getEnd());
        activityInBdd.setPrice(activityDTO.getPrice());
        activityInBdd.setImage(activityDTO.getImage());

        Activity activityUpdate = activityRepository.save(activityInBdd);

        return activityDTOMapper.toDTO(activityUpdate);
    }

    @Override
    public void delete(int id) throws ActivityException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ActivityException("Activity not found", NOT_FOUND));

        activityRepository.delete(activity);
    }
}
