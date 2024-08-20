package com.example.demo.mapper;

import com.example.demo.dto.ActivityDTO;
import com.example.demo.dto.ActivityDetailDTO;
import com.example.demo.entity.Activity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityDTOMapper {

    @Mapping(target = "travel", ignore = true)
    Activity toEntity(ActivityDTO activityDTO);

    @Named(value = "useMe")
    ActivityDTO toDTO(Activity activity);

    @Mapping(source = "travel", target = "travelDTO")
    ActivityDetailDTO toDetailDTO(Activity activity);

    @IterableMapping(qualifiedByName = "useMe")
    List<ActivityDTO> toDTOList(List<Activity> activities);
}
