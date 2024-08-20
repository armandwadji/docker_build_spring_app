package com.example.demo.controller;

import com.example.demo.controller.advice.CrudControllerAdviceInterface;
import com.example.demo.dto.ContactDTO;
import com.example.demo.exception.ContactException;
import com.example.demo.service.ContactService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequestMapping(path= "contact")
@RestController
public class ContactController implements CrudControllerAdviceInterface<ContactDTO> {
    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ContactDTO create( ContactDTO contactDTO) {
        return contactService.create(contactDTO);
    }

    @Override
    public List<ContactDTO> reads() {

        return contactService.list();
    }

    @Override
    public ContactDTO read(int id) throws ContactException {
        return contactService.read(id);
    }

    @Override
    public ContactDTO update(int id, ContactDTO contactDTO) throws ContactException {
        return contactService.update(id, contactDTO);
    }

    @Override
    public void delete(int id) throws ContactException {
        contactService.delete(id);
    }
}

