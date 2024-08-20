package com.example.demo.controller;

import com.example.demo.controller.advice.CrudControllerAdviceInterface;
import com.example.demo.dto.AccommodationDTO;
import com.example.demo.dto.AccommodationDetailDTO;
import com.example.demo.entity.Accommodation;
import com.example.demo.exception.AccommodationException;
import com.example.demo.service.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path= "accommodations")     // nom de la route dans l'API
@RestController
//@AllArgsConstructor                           // utilisation de lombok pour construire le constructeur

public class AccommodationController implements CrudControllerAdviceInterface<AccommodationDTO> {
    private AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @Override
    public AccommodationDTO create( AccommodationDTO accommodation) {
       return accommodationService.create(accommodation);
    }

    @Override
    public List<AccommodationDTO> reads() {

        return accommodationService.list();
    }

    @Override
    public AccommodationDetailDTO read(int id) throws AccommodationException {
        return accommodationService.read(id);
    }

    @Override
    public AccommodationDTO update(int id, AccommodationDTO accommodationDTO) throws AccommodationException {
        return accommodationService.update(id, accommodationDTO);
    }

    @Override
    public void delete(int id) throws AccommodationException {
        accommodationService.delete(id);
    }
}
