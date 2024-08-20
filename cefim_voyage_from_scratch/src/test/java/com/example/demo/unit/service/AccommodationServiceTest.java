package com.example.demo.unit.service;

import com.example.demo.dto.AccommodationDTO;
import com.example.demo.dto.AccommodationDetailDTO;
import com.example.demo.dto.TravelDTO;
import com.example.demo.entity.Accommodation;
import com.example.demo.entity.Travel;
import com.example.demo.exception.AccommodationException;
import com.example.demo.mapper.AccommodationDTOMapper;
import com.example.demo.mapper.TravelDTOMapper;
import com.example.demo.repository.AccommodationRepository;
import com.example.demo.service.AccommodationService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    private static final Faker FAKER = new Faker(new Locale("fr"));

    @Mock
    AccommodationRepository accommodationRepository;

    @Mock
    AccommodationDTOMapper accommodationDTOMapper;

    @MockBean
    TravelDTOMapper travelDTOMapper;

    @InjectMocks
    AccommodationService accommodationService;

    @Test
    @DisplayName("[AccommodationService] Test de création d'un logement")
    void shouldCreateAccommodations() {
        //GIVEN
        long accommodationId = 1L;
        AccommodationDTO accommodationDTO = AccommodationDTO.builder().name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build();
        Accommodation accommodation = Accommodation.builder().name(accommodationDTO.getName()).address(accommodationDTO.getAddress()).phone(accommodationDTO.getPhone()).price(accommodationDTO.getPrice()).start(accommodationDTO.getStart()).end(accommodationDTO.getEnd()).build();
        Accommodation accommodationSaved = Accommodation.builder().id(accommodationId).name(accommodationDTO.getName()).address(accommodationDTO.getAddress()).phone(accommodationDTO.getPhone()).price(accommodationDTO.getPrice()).start(accommodationDTO.getStart()).end(accommodationDTO.getEnd()).build();
        AccommodationDTO expected = AccommodationDTO.builder().id(accommodationId).name(accommodationDTO.getName()).address(accommodationDTO.getAddress()).phone(accommodationDTO.getPhone()).price(accommodationDTO.getPrice()).start(accommodationDTO.getStart()).end(accommodationDTO.getEnd()).build();

        Mockito.when(accommodationDTOMapper.toEntity(accommodationDTO)).thenReturn(accommodation);
        Mockito.when(accommodationRepository.save(accommodation)).thenReturn(accommodationSaved);
        Mockito.when(accommodationDTOMapper.toDto(accommodationSaved)).thenReturn(expected);

        //WHEN
        AccommodationDTO result = accommodationService.create(accommodationDTO);

        //THEN
        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("[AccommodationService] Test de récupération de la liste des logements")
    void shouldGetAllAccommodations() {

        //GIVEN
        List<Accommodation> accommodations = List.of(
                Accommodation.builder().name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build(),
                Accommodation.builder().name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build()
        );

        List<AccommodationDTO> expected = List.of(
                AccommodationDTO.builder().name(accommodations.get(0).getName()).address(accommodations.get(0).getAddress()).phone(accommodations.get(0).getPhone()).price(accommodations.get(0).getPrice()).start(accommodations.get(0).getStart()).end(accommodations.get(0).getEnd()).build(),
                AccommodationDTO.builder().name(accommodations.get(1).getName()).address(accommodations.get(1).getAddress()).phone(accommodations.get(1).getPhone()).price(accommodations.get(1).getPrice()).start(accommodations.get(1).getStart()).end(accommodations.get(1).getEnd()).build()
        );

        Mockito.when(accommodationRepository.findAll()).thenReturn(accommodations);
        Mockito.when(accommodationDTOMapper.toDtoList(accommodations)).thenReturn(expected);

        //WHEN
        List<AccommodationDTO> result = accommodationService.list();

        //THEN
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    @DisplayName("[AccommodationService] Test de récupération d'un logement par son ID")
    void shouldFindAccommodationById() throws AccommodationException {
        //GIVEN
        int travelId = 1;
        long accommodationId = 1L;
        Travel travel = Travel.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        Accommodation accommodation = Accommodation.builder().travel(travel).id(accommodationId).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build();

        TravelDTO travelDTO = TravelDTO.builder().id(travelId).title(travel.getTitle()).description(travel.getDescription()).location(travel.getLocation()).start(travel.getStart()).end(travel.getEnd()).build();
        AccommodationDetailDTO expected = AccommodationDetailDTO.builder().travelDTO(travelDTO).id(accommodation.getId()).name(accommodation.getName()).address(accommodation.getAddress()).phone(accommodation.getPhone()).price(accommodation.getPrice()).start(accommodation.getStart()).end(accommodation.getEnd()).build();

        Mockito.when(accommodationRepository.findById(Math.toIntExact(accommodationId))).thenReturn(Optional.of(accommodation));
        Mockito.when(accommodationDTOMapper.toDetailDTO(accommodation)).thenReturn(expected);

        //WHEN
        AccommodationDetailDTO result = accommodationService.read(Math.toIntExact(accommodationId));

        //THEN
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);

    }

    @Test
    @DisplayName("[AccommodationService] Test de l'exception récupération d'un logement non existant")
    void shouldFindAccommodationByIdThrowException() {
        //GIVEN
        int accommodationId = 1;
        Mockito.when(accommodationRepository.findById(any())).thenReturn(Optional.empty());

        //WHEN
        AccommodationException accommodationException = Assertions.assertThrows(AccommodationException.class, () -> accommodationService.read(accommodationId));

        //THEN
        assertThat(accommodationException.getHttpStatus()).isEqualTo(NOT_FOUND);
        assertThat(accommodationException.getMessage()).isEqualTo("Accommodation not found");
    }

    @Test
    @DisplayName("[AccommodationService] Test de l'éditons d'un logement par son ID")
    void shouldUpdateAccommodation() throws AccommodationException {
        //GIVEN
        long accommodationId = 1L;
        AccommodationDTO accommodationDTO = AccommodationDTO.builder().id(accommodationId).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build();
        Accommodation accommodation = Accommodation.builder().name(accommodationDTO.getName()).address(accommodationDTO.getAddress()).phone(accommodationDTO.getPhone()).price(accommodationDTO.getPrice()).start(accommodationDTO.getStart()).end(accommodationDTO.getEnd()).build();
        Accommodation accommodationUpdated = Accommodation.builder().id(accommodationId).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build();
        AccommodationDTO expected = AccommodationDTO.builder().id(accommodationId).name(accommodationUpdated.getName()).address(accommodationUpdated.getAddress()).phone(accommodationUpdated.getPhone()).price(accommodationUpdated.getPrice()).start(accommodationUpdated.getStart()).end(accommodationUpdated.getEnd()).build();

        Mockito.when(accommodationRepository.findById((int) accommodationId)).thenReturn(Optional.of(accommodation));
        Mockito.when(accommodationRepository.save(accommodation)).thenReturn(accommodationUpdated);
        Mockito.when(accommodationDTOMapper.toDto(accommodationUpdated)).thenReturn(expected);

        //WHEN
        AccommodationDTO result = accommodationService.update((int) accommodationId, accommodationDTO);

        //THEN
        assertThat(result).isNotNull();
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    @DisplayName("[AccommodationService] Test de la suppression d'un logement par son ID")
    void shouldDeleteTravel() throws AccommodationException {
        //GIVEN
        long accommodationId = 1;
        Accommodation accommodation = Accommodation.builder().id(accommodationId).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build();
        Mockito.when(accommodationRepository.findById((int) accommodationId)).thenReturn(Optional.of(accommodation));

        //WHEN
        accommodationService.delete((int) accommodationId);

        //THEN
        Mockito.verify(accommodationRepository, Mockito.times(1)).delete(accommodation);
        Mockito.verify(accommodationRepository).delete(accommodation);

    }
}
