package com.example.demo.mapper;

import com.example.demo.dto.AccommodationDTO;
import com.example.demo.dto.AccommodationDetailDTO;
import com.example.demo.entity.Accommodation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TravelDTOMapper.class})
public interface AccommodationDTOMapper {

    @Mapping(target = "travel", ignore = true)
    Accommodation toEntity(AccommodationDTO accommodationDTO) ;

    @Named(value = "useMe")
    AccommodationDTO toDto(Accommodation accommodation);

    @Mapping(source = "travel", target = "travelDTO")
    AccommodationDetailDTO toDetailDTO(Accommodation accommodation);

    @IterableMapping(qualifiedByName = "useMe")
    List<AccommodationDTO> toDtoList(List<Accommodation> accommodations);

}
