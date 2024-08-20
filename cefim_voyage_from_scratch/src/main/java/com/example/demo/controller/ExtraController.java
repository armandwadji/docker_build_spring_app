package com.example.demo.controller;

import com.example.demo.controller.advice.CrudControllerAdviceInterface;
import com.example.demo.dto.ExtraDTO;
import com.example.demo.dto.ExtraDetailDTO;
import com.example.demo.exception.ExtraException;
import com.example.demo.service.ExtraService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path= "extras")               // nom de la route dans l'API
@RestController
//@AllArgsConstructor                           // utilisation de lombok pour construire le constructeur

public class ExtraController implements CrudControllerAdviceInterface<ExtraDTO> {
    private ExtraService extraService;

    public ExtraController(ExtraService extraService) {
        this.extraService = extraService;
    }

    @Override
    public ExtraDTO create( ExtraDTO extraDTO) {
        return extraService.create(extraDTO);
    }

    @Override
    public List<ExtraDTO> reads() {
        return extraService.list();
    }

    @Override
    public ExtraDetailDTO read(int id) throws ExtraException {
        return extraService.read(id);
    }

    @Override
    public ExtraDTO update(int id, ExtraDTO extraDTO) throws ExtraException {
        return extraService.update(id, extraDTO);
    }

    @Override
    public void delete(int id) throws ExtraException {
        extraService.delete(id);
    }
}
