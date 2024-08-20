package com.example.demo.service.graphqlService;

import com.example.demo.dto.ExtraDTO;
import com.example.demo.entity.Extra;
import com.example.demo.entity.Travel;
import com.example.demo.exception.ExtraException;
import com.example.demo.exception.TravelException;
import com.example.demo.repository.ExtraRepository;
import com.example.demo.repository.TravelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service @AllArgsConstructor
public class ExtraGraphQLService {

    public static final String EXTRA_NOT_FOUND = "Extra Not Found";
    private ExtraRepository extraRepository;
    private TravelRepository travelRepository;

    public List<Extra> extras (){return extraRepository.findAll();}

    public Extra extra(int id) throws ExtraException {return extraRepository.findById(id).orElseThrow(() -> new ExtraException(EXTRA_NOT_FOUND, NOT_FOUND));}

    public Extra create(int travelId, ExtraDTO extraDTO) throws TravelException {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new TravelException("Travel Not Found", NOT_FOUND));
        Extra extra = Extra.builder()
                .name(extraDTO.getName())
                .price(extraDTO.getPrice())
                .createdat(extraDTO.getCreatedat())
                .travel(travel)
                .build();

        return extraRepository.save(extra);
    }

    public Extra update(int id, ExtraDTO extraDTO) throws ExtraException {
        Extra extra  = extraRepository.findById(id).orElseThrow(() -> new ExtraException(EXTRA_NOT_FOUND, NOT_FOUND));
        extra.setName(extraDTO.getName());
        extra.setPrice(extraDTO.getPrice());
        extra.setCreatedat(extraDTO.getCreatedat());

        return extraRepository.save(extra);
    }

    public String delete(int id) throws ExtraException {
        Extra extra  = extraRepository.findById(id).orElseThrow(() -> new ExtraException(EXTRA_NOT_FOUND, NOT_FOUND));
        extraRepository.delete(extra);

        return "Extra delete Successfully";
    }
}
