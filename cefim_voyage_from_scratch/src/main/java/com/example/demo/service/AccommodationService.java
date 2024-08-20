package com.example.demo.service;


import com.example.demo.dto.AccommodationDTO;
import com.example.demo.dto.AccommodationDetailDTO;
import com.example.demo.entity.Accommodation;
import com.example.demo.exception.AccommodationException;
import com.example.demo.mapper.AccommodationDTOMapper;
import com.example.demo.repository.AccommodationRepository;
import com.example.demo.service.interfaces.CrudServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor         // utilisation de lombok pour construire le constructeur
public class AccommodationService implements CrudServiceInterface<AccommodationDTO> {
    private AccommodationRepository accommodationRepository;
    private AccommodationDTOMapper accommodationDTOMapper;
    @Override
    public AccommodationDTO create(AccommodationDTO accommodationDTO) {
        Accommodation accommodation = accommodationDTOMapper.toEntity(accommodationDTO);
        Accommodation accommodationSave = accommodationRepository.save(accommodation);
        return accommodationDTOMapper.toDto(accommodationSave);
    }

    @Override
    public List<AccommodationDTO> list() {
        return accommodationDTOMapper.toDtoList(accommodationRepository.findAll());
    }

    @Override
    public AccommodationDetailDTO read(int id) throws AccommodationException {

         return accommodationRepository.findById(id)
                 .map(accommodationDTOMapper::toDetailDTO)
                .orElseThrow(() -> new AccommodationException("Accommodation not found", NOT_FOUND));
    }

    @Override
    public AccommodationDTO update(int id, AccommodationDTO accommodationDTO) throws AccommodationException {
        Accommodation accommodationInBdd = accommodationRepository.findById(id)
                .orElseThrow(() -> new AccommodationException("Accommodation not found", NOT_FOUND));

        accommodationInBdd.setName(accommodationDTO.getName());
        accommodationInBdd.setAddress(accommodationDTO.getAddress());
        accommodationInBdd.setPhone(accommodationDTO.getPhone());
        accommodationInBdd.setPrice(accommodationDTO.getPrice());
        accommodationInBdd.setStart(accommodationDTO.getStart());
        accommodationInBdd.setEnd(accommodationDTO.getEnd());

        Accommodation accommodationUpdate = accommodationRepository.save(accommodationInBdd);

        return accommodationDTOMapper.toDto(accommodationUpdate);
    }

    @Override
    public void delete(int id) throws AccommodationException {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new AccommodationException("Accommodation not found", NOT_FOUND));
        accommodationRepository.delete(accommodation);
    }
}
