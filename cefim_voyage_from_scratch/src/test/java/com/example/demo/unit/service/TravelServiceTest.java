package com.example.demo.unit.service;

import com.example.demo.dto.TravelDTO;
import com.example.demo.entity.Travel;
import com.example.demo.exception.TravelException;
import com.example.demo.mapper.TravelDTOMapper;
import com.example.demo.repository.TravelRepository;
import com.example.demo.service.TravelService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class TravelServiceTest {
    private static final Faker FAKER = new Faker(new Locale("fr"));

    @Mock
    TravelRepository travelRepository;

    @Mock
    TravelDTOMapper travelDTOMapper;

    @InjectMocks
    TravelService travelService;

    @Test
    @DisplayName("[TravelService] Test de création d'un voyage")
    void shouldCreateTravel() {
        //GIVEN
        int travelId = 1;
        TravelDTO travelDto = TravelDTO.builder().title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        Travel travel = Travel.builder().title(travelDto.getTitle()).description(travelDto.getDescription()).location(travelDto.getLocation()).start(travelDto.getStart()).end(travelDto.getEnd()).build();
        Travel travelSaved = Travel.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        TravelDTO expected = TravelDTO.builder().id(travelId).title(travelSaved.getTitle()).description(travelSaved.getDescription()).location(travelSaved.getLocation()).start(travelSaved.getStart()).end(travelSaved.getEnd()).build();
        Mockito.when(travelDTOMapper.toEntity(travelDto)).thenReturn(travel);
        Mockito.when(travelRepository.save(travel)).thenReturn(travelSaved);
        Mockito.when(travelDTOMapper.toDTO(travelSaved)).thenReturn(expected);

        //WHEN
        TravelDTO result = travelService.create(travelDto);

        //THEN
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("[TravelService] Test de récupération de la liste des voyages")
    void shouldGetAllTravels() {
        //GIVEN
        List<Travel> travels = List.of(
                Travel.builder().title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build(),
                Travel.builder().title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build()
        );

        List<TravelDTO> expected = List.of(
                TravelDTO.builder().title(travels.get(0).getTitle()).description(travels.get(0).getDescription()).location(travels.get(0).getLocation()).start(travels.get(0).getStart()).end(travels.get(0).getEnd()).build(),
                TravelDTO.builder().title(travels.get(1).getTitle()).description(travels.get(1).getDescription()).location(travels.get(1).getLocation()).start(travels.get(1).getStart()).end(travels.get(1).getEnd()).build()
        );

        Mockito.when(travelRepository.findAll()).thenReturn(travels);
        Mockito.when(travelDTOMapper.toDTOList(travels)).thenReturn(expected);

        //WHEN
        List<TravelDTO> result = travelService.list();

        //THEN
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    @DisplayName("[TravelService] Test de récupération d'un voyage par son ID")
    void shouldFindTravelById() throws TravelException {
        //GIVEN
        int travelId = 1;
        Travel travel = Travel.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        TravelDTO expected = TravelDTO.builder().id(travelId).title(travel.getTitle()).description(travel.getDescription()).location(travel.getLocation()).start(travel.getStart()).end(travel.getEnd()).build();

        Mockito.when(travelRepository.findById(travelId)).thenReturn(Optional.of(travel));
        Mockito.when(travelDTOMapper.toDTO(travel)).thenReturn(expected);

        //WHEN
        TravelDTO result = travelService.read(travelId);

        //THEN
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    @DisplayName("[TravelService] Test de l'exception récupération d'un voyage non existant")
    void shouldFindTravelByIdThrowException() {
        //GIVEN
        int travelId = 1;
        Mockito.when(travelRepository.findById(any())).thenReturn(Optional.empty());

        //WHEN
        TravelException travelException = Assertions.assertThrows(TravelException.class, () -> travelService.read(travelId));

        //THEN
        assertThat(travelException.getHttpStatus()).isEqualTo(NOT_FOUND);
        assertThat(travelException.getMessage()).isEqualTo("Travel not Found");

    }

    @Test
    @DisplayName("[TravelService] Test de l'éditons d'un voyage par son ID")
    void shouldUpdateTravel() throws TravelException {
        //GIVEN
        int travelId = 1;
        TravelDTO travelDto = TravelDTO.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        Travel travel = Travel.builder().id(travelId).title(travelDto.getTitle()).description(travelDto.getDescription()).location(travelDto.getLocation()).start(travelDto.getStart()).end(travelDto.getEnd()).build();
        Travel travelUpdated = Travel.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        TravelDTO expected = TravelDTO.builder().id(travelId).title(travelUpdated.getTitle()).description(travelUpdated.getDescription()).location(travelUpdated.getLocation()).start(travelUpdated.getStart()).end(travelUpdated.getEnd()).build();

        Mockito.when(travelRepository.findById(travelId)).thenReturn(Optional.of(travel));
        Mockito.when(travelRepository.save(travel)).thenReturn(travelUpdated);
        Mockito.when(travelDTOMapper.toDTO(travelUpdated)).thenReturn(expected);

        //WHEN
        TravelDTO result = travelService.update(travelId, travelDto);

        //THEN
        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    @DisplayName("[TravelService] Test de la suppression d'un voyage par son ID")
    void shouldDeleteTravel() throws TravelException {
        //GIVEN
        int travelId = 1;
        Travel travel = Travel.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        Mockito.when(travelRepository.findById(travelId)).thenReturn(Optional.of(travel));

        //WHEN
        travelService.delete(travelId);

        //THEN
        Mockito.verify(travelRepository, Mockito.times(1)).delete(travel);
        Mockito.verify(travelRepository).delete(travel);
    }
}
