package com.example.demo.mapper;

import com.example.demo.dto.ContactDTO;
import com.example.demo.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactDTOMapper {

    @Mapping(target = "activity", ignore = true)
    Contact toEntity(ContactDTO contactDTO);

    ContactDTO toDto(Contact contact);

    List<ContactDTO> toDtoList(List<Contact> contacts);

}
