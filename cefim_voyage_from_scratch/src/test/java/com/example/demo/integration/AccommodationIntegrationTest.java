package com.example.demo.integration;

import com.example.demo.dto.AccommodationDTO;
import com.example.demo.dto.AccommodationDetailDTO;
import com.example.demo.entity.Accommodation;
import com.example.demo.entity.Travel;
import com.example.demo.exception.AccommodationException;
import com.example.demo.repository.AccommodationRepository;
import com.example.demo.repository.TravelRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class AccommodationIntegrationTest {

    private static final Faker FAKER = new Faker(new Locale("fr"));

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    List<Travel> travels;
    List<Accommodation> accommodations = new ArrayList<>();

    @BeforeEach
    void setUp() {
        travels = travelRepository.saveAll(
                List.of(
                        Travel.builder().id(1).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build(),
                        Travel.builder().id(2).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build(),
                        Travel.builder().id(3).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build()
                )
        );

        travels.forEach(travel -> {
            Accommodation accommodation  = accommodationRepository.save(Accommodation.builder().id((long) travel.getId()).travel(travel).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build());
            accommodations.add(accommodation);
        });
    }

    @Container
    @ServiceConnection
    private static MariaDBContainer mariaDBContainer=new MariaDBContainer("mariadb:10.6");

    @Test
    @Order(value = 1)
    @DisplayName("[AccommodationIntegrationTest] Test de récupération de la liste des logements")
    void testGetAllAccommodations(){
        ResponseEntity<AccommodationDTO[]> response = testRestTemplate.exchange("/accommodations", GET, null, AccommodationDTO[].class);
        List<AccommodationDTO> content = Arrays.asList(Objects.requireNonNull(response.getBody()));

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(content.size()).isEqualTo(accommodations.size());
        assertThat(content).usingRecursiveComparison().ignoringFields("start", "end").isEqualTo(accommodations);
    }

    @Test
    @Order(value = 2)
    @DisplayName("[AccommodationIntegrationTest] Test de création d'un logement")
    void shouldCreateAccommodation() {
        AccommodationDTO accommodationDTO = AccommodationDTO.builder().name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build();
        ResponseEntity<AccommodationDTO> response = testRestTemplate.exchange("/accommodations" , POST, new HttpEntity<>(accommodationDTO), AccommodationDTO.class);
        AccommodationDTO content = Objects.requireNonNull(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(content).usingRecursiveComparison().ignoringFields("id").isEqualTo(accommodationDTO);
    }

    @Test
    @DisplayName("[AccommodationIntegrationTest] Test de récupération d'un logement par son ID")
    void testGetAccommodationById(){
        ResponseEntity<AccommodationDetailDTO> response = testRestTemplate.exchange("/accommodations/1" , GET, null, AccommodationDetailDTO.class);
        AccommodationDetailDTO content = Objects.requireNonNull(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(content).usingRecursiveComparison().ignoringFields("start", "end", "travelDTO").isEqualTo(accommodations.get(0));
        assertThat(content.getTravelDTO()).usingRecursiveComparison().isEqualTo(travels.get(0));
    }

    @Test
    @DisplayName("[AccommodationIntegrationTest] Test de l'exception récupération d'un logement non existant")
    void shouldFindAccommodationByIdThrowException() {
        int accommodationId = 100;
        ResponseEntity<AccommodationException> response = response = testRestTemplate.exchange("/accommodations/" + accommodationId , GET, null, AccommodationException.class);
        AccommodationException  content = Objects.requireNonNull(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(content.getMessage()).isEqualTo("Accommodation not found");
    }

    @Test
    @DisplayName("[AccommodationIntegrationTest] Test de l'éditons d'un logement par son ID")
    void testUpdateAccommodation() {
        int accommodationId = 1;
        AccommodationDTO accommodationUpdate = AccommodationDTO.builder().name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build();
        ResponseEntity<AccommodationDTO> response = testRestTemplate.exchange("/accommodations/" + accommodationId , PUT, new HttpEntity<>(accommodationUpdate), AccommodationDTO.class);
        AccommodationDTO content = Objects.requireNonNull(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(ACCEPTED);
        assertThat(content.getId()).isEqualTo(accommodationId);
        assertThat(content).usingRecursiveComparison().ignoringFields("id").isEqualTo(accommodationUpdate);
    }

    @Test
    @DisplayName("[AccommodationIntegrationTest] Test de la suppression d'un logement par son ID")
    public void testDeleteAccommodation() {
        int accommodationId = 2;
        ResponseEntity<String> response = testRestTemplate.exchange("/accommodations/" + accommodationId , DELETE, null, String.class);
        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);

        ResponseEntity<AccommodationException> responseException = testRestTemplate.exchange("/accommodations/" + accommodationId , GET, null, AccommodationException.class);
        AccommodationException  content = Objects.requireNonNull(responseException.getBody());

        assertThat(responseException.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(content.getMessage()).isEqualTo("Accommodation not found");
    }
}
