package com.example.demo.service;

import com.example.demo.dto.TravelDTO;
import com.example.demo.entity.Travel;
import com.example.demo.exception.TravelException;
import com.example.demo.mapper.TravelDTOMapper;
import com.example.demo.repository.TravelRepository;
import com.example.demo.service.interfaces.CrudServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service @AllArgsConstructor
public class TravelService implements CrudServiceInterface<TravelDTO> {

    public static final String TRAVEL_NOT_FOUND = "Travel not Found";
    private final TravelRepository travelRepository;
    private final TravelDTOMapper travelDTOMapper;

    /**
     * Méthode permettant de créer un voyage
     * @param travelDTO : "paramètre d'entrer"
     * @return TravelDTO
     */
    @Override
    public TravelDTO create(TravelDTO travelDTO) {
        Travel travelSave = travelRepository.save(travelDTOMapper.toEntity(travelDTO));
        return travelDTOMapper.toDTO(travelSave);
    }

    /**
     * Méthode permettant d'avoir la liste des voyages
     * @return List<TravelDTO>
     */
    @Override
    public List<TravelDTO> list() {
        return travelDTOMapper.toDTOList(travelRepository.findAll());
    }

    /**
     * Méthode permettant de lire le détail d'un voyage
     * @param id : "id du voyage"
     * @return TravelDTO
     * @throws TravelException : " Exception d'un voyage"
     */
    @Override
    public TravelDTO read(int id) throws TravelException {
        return travelRepository.findById(id)
                .map(travelDTOMapper::toDTO)
                .orElseThrow(() -> new TravelException(TRAVEL_NOT_FOUND, NOT_FOUND));
    }

    /**
     * Méthode permettant d'éditer un voyage
     * @param id : "id du voyage"
     * @param travelDTO : "paramètre d'entrer"
     * @return TravelDTO
     * @throws TravelException : " Exception d'un voyage"
     */
    @Override
    public TravelDTO update(int id, TravelDTO travelDTO) throws TravelException {
        Travel travelInDataBase = travelRepository.findById(id).orElseThrow(() -> new TravelException(TRAVEL_NOT_FOUND, NOT_FOUND));
        travelInDataBase.setTitle(travelDTO.getTitle());
        travelInDataBase.setDescription(travelDTO.getDescription());
        travelInDataBase.setLocation(travelDTO.getLocation());
        travelInDataBase.setStart(travelDTO.getStart());
        travelInDataBase.setEnd(travelDTO.getEnd());

        Travel travelUpdate = travelRepository.save(travelInDataBase);
        return travelDTOMapper.toDTO(travelUpdate);
    }

    /**
     * Méthode permettant de supprimer un voyage
     * @param id : "id du voyage"
     * @throws TravelException : " Exception d'un voyage"
     */
    @Override
    public void delete(int id) throws TravelException {
        Travel travel = travelRepository.findById(id).orElseThrow(() -> new TravelException(TRAVEL_NOT_FOUND, NOT_FOUND));
        travelRepository.delete(travel);
    }
}
