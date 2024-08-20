package com.example.demo.mapper;

import com.example.demo.dto.ExtraDTO;
import com.example.demo.dto.ExtraDetailDTO;
import com.example.demo.entity.Extra;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TravelDTOMapper.class})
public interface ExtraDTOMapper {

    @Mapping(target = "travel", ignore = true)
    Extra toEntity(ExtraDTO extraDTO);

    @Named(value = "useMe")
    ExtraDTO toDTO(Extra extra);

    @IterableMapping(qualifiedByName = "useMe")
    List<ExtraDTO> toDTOList(List<Extra> extras);

    @Mapping(source = "travel", target = "travelDTO")
    ExtraDetailDTO toDetailDTO(Extra extra);

}