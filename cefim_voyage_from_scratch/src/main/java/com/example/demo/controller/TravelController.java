package com.example.demo.controller;

import com.example.demo.controller.advice.CrudControllerAdviceInterface;
import com.example.demo.dto.TravelDTO;
import com.example.demo.exception.TravelException;
import com.example.demo.service.TravelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "travels")
public class TravelController implements CrudControllerAdviceInterface<TravelDTO> {

    private final TravelService travelService;
    @Override
    public TravelDTO create(TravelDTO travelDTO) {
        return travelService.create(travelDTO);
    }

    @Override
    public List<TravelDTO> reads() {
        return travelService.list();
    }

    @Override
    public TravelDTO read(int id) throws TravelException {
        return travelService.read(id);
    }

    @Override
    public TravelDTO update(int id, TravelDTO travelDTO) throws TravelException {
        return travelService.update(id, travelDTO);
    }

    @Override
    public void delete(int id) throws TravelException {
        travelService.delete(id);
    }
}
