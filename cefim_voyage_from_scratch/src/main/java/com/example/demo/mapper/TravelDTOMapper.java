package com.example.demo.mapper;

import com.example.demo.dto.TravelDTO;
import com.example.demo.entity.Travel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TravelDTOMapper {

    @Mapping(source = "id", target = "id", ignore = true)
    Travel toEntity(TravelDTO travelDTO);

    TravelDTO toDTO(Travel travel);

    List<TravelDTO> toDTOList(List<Travel> travels);
}
