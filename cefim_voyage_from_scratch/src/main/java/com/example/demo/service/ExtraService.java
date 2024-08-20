package com.example.demo.service;

import com.example.demo.dto.ExtraDTO;
import com.example.demo.dto.ExtraDetailDTO;
import com.example.demo.entity.Extra;
import com.example.demo.exception.ExtraException;
import com.example.demo.mapper.ExtraDTOMapper;
import com.example.demo.repository.ExtraRepository;
import com.example.demo.service.interfaces.CrudServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor             // utilisation de lombok pour construire le constructeur
public class ExtraService implements CrudServiceInterface<ExtraDTO> {
    private ExtraRepository extraRepository;
    private ExtraDTOMapper extraDTOMapper;

    @Override
    public ExtraDTO create(ExtraDTO extraDTO) {
        Extra extra = extraDTOMapper.toEntity(extraDTO);
        Extra extraSave = extraRepository.save(extra);

        return extraDTOMapper.toDTO(extraSave);
    }

    @Override
    public List<ExtraDTO> list() {
        return extraDTOMapper.toDTOList(extraRepository.findAll());
    }


    @Override
    public ExtraDetailDTO read(int id) throws ExtraException {
        return extraRepository.findById(id)
                .map(extraDTOMapper::toDetailDTO)
                .orElseThrow(() -> new ExtraException("Extra not found", NOT_FOUND));
    }

    @Override
    public ExtraDTO update(int id, ExtraDTO extraDTO) throws ExtraException {
        Extra extraInBdd = extraRepository.findById(id)
                .orElseThrow(
                        () -> new ExtraException("Extra not found", NOT_FOUND)
                );
        extraInBdd.setName(extraDTO.getName());
        extraInBdd.setPrice(extraDTO.getPrice());

        return extraDTOMapper.toDTO(extraRepository.save(extraInBdd));
    }

    @Override
    public void delete(int id) throws ExtraException {
        Extra extra = extraRepository.findById(id)
                .orElseThrow(
                        () -> new ExtraException("Extra not found", NOT_FOUND)
                );
        extraRepository.delete(extra);
    }
}
