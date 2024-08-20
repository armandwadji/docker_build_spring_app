package com.example.demo.unit.mapper;

import com.example.demo.dto.TravelDTO;
import com.example.demo.entity.Travel;
import com.example.demo.mapper.TravelDTOMapper;
import com.example.demo.mapper.TravelDTOMapperImpl;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TravelDTOMapperTest {
    private static final Faker FAKER = new Faker(new Locale("fr"));
    private TravelDTOMapper travelDTOMapper = new TravelDTOMapperImpl();

    @Test
    @DisplayName("[TravelMapper] Test de DTO -> Entity")
    void toEntity() {
        //GIVEN
        TravelDTO given = TravelDTO.builder().title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        Travel expected = Travel.builder().title(given.getTitle()).description(given.getDescription()).location(given.getLocation()).start(given.getStart()).end(given.getEnd()).build();

        //WHEN
        Travel result = travelDTOMapper.toEntity(given);

        //THEN
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("[TravelMapper] Test de Entity -> DTO")
    void toDTO() {
        //GIVEN
        Travel given = Travel.builder().title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        TravelDTO expected = TravelDTO.builder().title(given.getTitle()).description(given.getDescription()).location(given.getLocation()).start(given.getStart()).end(given.getEnd()).build();

        //WHEN
        TravelDTO result = travelDTOMapper.toDTO(given);

        //THEN
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("[TravelMapper] Test de List<Entity> -> List<DTO>")
    void toDTOList() {
        //GIVEN
        List<Travel> given = List.of(
                Travel.builder().title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build(),
                Travel.builder().title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build()
        );

        List<TravelDTO> expected = List.of(
                TravelDTO.builder().title(given.get(0).getTitle()).description(given.get(0).getDescription()).location(given.get(0).getLocation()).start(given.get(0).getStart()).end(given.get(0).getEnd()).build(),
                TravelDTO.builder().title(given.get(1).getTitle()).description(given.get(1).getDescription()).location(given.get(1).getLocation()).start(given.get(1).getStart()).end(given.get(1).getEnd()).build()
        );

        //WHEN
        List<TravelDTO> result = travelDTOMapper.toDTOList(given);

        //THEN
        assertThat(expected).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    @DisplayName("[TravelMapper] Test de DTO(null) -> Entity(null)")
    void shouldNotMapNullCustomerToCustomerDTO() {
        //GIVEN
        Travel given = null;
        //WHEN
        TravelDTO result = travelDTOMapper.toDTO(given);
        //THEN
        assertThat(result).usingRecursiveComparison().isEqualTo(null);
    }

    @Test
    @DisplayName("[TravelMapper] Test de Entity(null) -> DTO(null)")
    void shouldNotMapNullCustomerDTOToCustomer() {
        //GIVEN
        TravelDTO given = null;
        //WHEN
        Travel result = travelDTOMapper.toEntity(given);
        //THEN
        assertThat(result).usingRecursiveComparison().isEqualTo(null);
    }

    @Test
    @DisplayName("[TravelMapper] Test de List<Entity>(null) -> List<DTO>(null)")
    void shouldNotMapNullListCustomerToCustomerDTO() {
        //GIVEN
        List<Travel> travels = null;
        //WHEN
        List<TravelDTO> result = travelDTOMapper.toDTOList(travels);
        //THEN
        assertThat(result).usingRecursiveComparison().isEqualTo(null);
    }
}
