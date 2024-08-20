package com.example.demo.service;

import com.example.demo.dto.ContactDTO;
import com.example.demo.entity.Contact;
import com.example.demo.exception.ContactException;
import com.example.demo.mapper.ContactDTOMapper;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.interfaces.CrudServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class ContactService implements CrudServiceInterface<ContactDTO> {

    private ContactRepository contactRepository;
    private ContactDTOMapper contactDTOMapper;
    @Override
    public ContactDTO create(ContactDTO contactDTO) {
        Contact contact = contactDTOMapper.toEntity(contactDTO);
        Contact contactSave = contactRepository.save(contact);

        return contactDTOMapper.toDto(contactSave);
    }

    @Override
    public List<ContactDTO> list() {
       return contactDTOMapper.toDtoList(contactRepository.findAll());
    }

    @Override
    public ContactDTO read(int id) throws ContactException {

        return contactRepository.findById(id)
                .map(contactDTOMapper::toDto)
                .orElseThrow(() -> new ContactException("Accommodation not found", NOT_FOUND));
    }

    @Override
    public ContactDTO update(int id, ContactDTO contactDTO) throws ContactException {
        Contact contactInBdd = contactRepository.findById(id)
                .orElseThrow(() -> new ContactException("Accommodation not found", NOT_FOUND));
        contactInBdd.setName(contactDTO.getName());
        contactInBdd.setPhone(contactDTO.getPhone());
        contactInBdd.setEmail(contactDTO.getEmail());

        Contact contactUpdate = contactRepository.save(contactInBdd);

        return contactDTOMapper.toDto(contactUpdate);
    }

    @Override
    public void delete(int id) throws ContactException {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactException("Contact not found", NOT_FOUND));
        contactRepository.delete(contact);
    }
}
