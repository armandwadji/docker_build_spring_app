package com.example.demo.service.graphqlService;

import com.example.demo.dto.AccommodationDTO;
import com.example.demo.entity.Accommodation;
import com.example.demo.entity.Travel;
import com.example.demo.exception.AccommodationException;
import com.example.demo.exception.TravelException;
import com.example.demo.repository.AccommodationRepository;
import com.example.demo.repository.TravelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service @AllArgsConstructor
public class AccommodationGraphQLService {

    private AccommodationRepository accommodationRepository;
    private TravelRepository travelRepository;

    public List<Accommodation> accommodations (){return accommodationRepository.findAll();}

    public Accommodation accommodation (int id) throws AccommodationException {return accommodationRepository.findById(id).orElseThrow(() -> new AccommodationException("Accommodation Not Found", NOT_FOUND));}

    public Accommodation create(int travelId, AccommodationDTO accommodationDTO) throws TravelException {
        Travel travel = travelRepository.findById(travelId).orElseThrow(() -> new TravelException("Travel Not Found", NOT_FOUND));


        Accommodation accommodation =Accommodation.builder()
                .name(accommodationDTO.getName())
                .address(accommodationDTO.getAddress())
                .phone(accommodationDTO.getPhone())
                .price(accommodationDTO.getPrice())
                .start(accommodationDTO.getStart())
                .end(accommodationDTO.getEnd())
                .travel(travel)
                .build();

        return accommodationRepository.save(accommodation);
    }

    public Accommodation update(int id, AccommodationDTO accommodationDTO) throws AccommodationException {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(() -> new AccommodationException("Accommodation Not Found", NOT_FOUND));

        accommodation.setName(accommodationDTO.getName());
        accommodation.setAddress(accommodationDTO.getAddress());
        accommodation.setPhone(accommodationDTO.getPhone());
        accommodation.setPrice(accommodationDTO.getPrice());
        accommodation.setStart(accommodationDTO.getStart());
        accommodation.setEnd(accommodationDTO.getEnd());

        return accommodationRepository.save(accommodation);
    }

    public String delete(int id) throws AccommodationException {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(() -> new AccommodationException("Accommodation Not Found", NOT_FOUND));
        accommodationRepository.delete(accommodation);
        return "Accommodation delete Successfully";
    }
}
