//package com.example.demo.integration;
//
//import com.example.demo.dto.TravelDTO;
//import com.example.demo.entity.Travel;
//import com.example.demo.exception.TravelException;
//import com.example.demo.repository.TravelRepository;
//import com.github.javafaker.Faker;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.testcontainers.containers.MariaDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//import java.util.Objects;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.springframework.http.HttpMethod.DELETE;
//import static org.springframework.http.HttpMethod.GET;
//import static org.springframework.http.HttpMethod.POST;
//import static org.springframework.http.HttpMethod.PUT;
//import static org.springframework.http.HttpStatus.ACCEPTED;
//import static org.springframework.http.HttpStatus.CREATED;
//import static org.springframework.http.HttpStatus.NOT_FOUND;
//import static org.springframework.http.HttpStatus.NO_CONTENT;
//import static org.springframework.http.HttpStatus.OK;
//
//@Testcontainers
//@ActiveProfiles("test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
//public class TravelIntegrationTest {
//
//    private static final Faker FAKER = new Faker(new Locale("fr"));
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Autowired
//    private TravelRepository travelRepository;
//
//    List<Travel> travels;
//
//    @BeforeEach
//    void setUp() {
//        int travelId = 1;
//        this.travels = List.of(
//                Travel.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build(),
//                Travel.builder().id(travelId + 1).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build(),
//                Travel.builder().id(travelId + 2).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build()
//        );
//        this.travelRepository.saveAll(travels);
//    }
//
//    @Container
//    @ServiceConnection
//    private static MariaDBContainer mariaDBContainer=new MariaDBContainer("mariadb:10.6");
//
//    @Test
//    @Order(value = 2)
//    @DisplayName("[TravelIntegrationTest] Test de création d'un voyage")
//    void shouldCreateTravel() {
//
//        //GIVEN
//        TravelDTO travelDTO = TravelDTO.builder().title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
//
//        //WHEN
//        ResponseEntity<TravelDTO> response = testRestTemplate.exchange("/travels", POST, new HttpEntity<>(travelDTO), TravelDTO.class);
//        TravelDTO content = Objects.requireNonNull(response.getBody());
//
//        //THEN
//        assertThat(response.getStatusCode()).isEqualTo(CREATED);
//        assertThat(content).usingRecursiveComparison().ignoringFields("id").isEqualTo(travelDTO);
//    }
//
//    @Test
//    @Order(value = 1)
//    @DisplayName("[TravelIntegrationTest] Test de récupération de la liste des voyages")
//    void testGetAllTravels(){
//
//        //GIVEN - WHEN
//        ResponseEntity<TravelDTO[]> response = testRestTemplate.exchange("/travels", GET, null, TravelDTO[].class);
//        List<TravelDTO> content = Arrays.asList(Objects.requireNonNull(response.getBody()));
//
//        //THEN
//        assertThat(response.getStatusCode()).isEqualTo(OK);
//        assertThat(content.size()).isEqualTo(travels.size());
//        assertThat(content).usingRecursiveComparison().isEqualTo(travels);
//    }
//
//    @Test
//    @DisplayName("[TravelIntegrationTest] Test de récupération d'un voyage par son ID")
//    void testGetTravelById(){
//
//        //GIVEN
//        ResponseEntity<TravelDTO> response = testRestTemplate.exchange("/travels/" + travels.get(0).getId(), GET, null, TravelDTO.class);
//        TravelDTO content = Objects.requireNonNull(response.getBody());
//
//        //THEN
//        assertThat(response.getStatusCode()).isEqualTo(OK);
//        assertThat(content).usingRecursiveComparison().isEqualTo(travels.get(content.getId() - 1));
//    }
//
//    @Test
//    @DisplayName("[TravelIntegrationTest] Test de l'exception récupération d'un voyage non existant")
//    void shouldFindTravelByIdThrowException() {
//        //GIVEN
//        int travelId = 10;
//
//        //WHEN
//        ResponseEntity<TravelException> response = testRestTemplate.exchange("/travels/" + travelId, GET, null, TravelException.class);
//        TravelException content = Objects.requireNonNull(response.getBody());
//
//        //THEN
//        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
//        assertThat(content.getMessage()).isEqualTo("Travel not Found");
//
//    }
//
//    @Test
//    @DisplayName("[TravelIntegrationTest] Test de l'éditons d'un voyage par son ID")
//    void testUpdateTrave() {
//        //GIVEN
//        int travelId = 1;
//        TravelDTO travelDTOUpdate = TravelDTO.builder().id(10).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
//
//        //WHEN
//        ResponseEntity<TravelDTO> response = testRestTemplate.exchange("/travels/" + travelId, PUT, new HttpEntity<>(travelDTOUpdate), TravelDTO.class);
//        TravelDTO content = Objects.requireNonNull(response.getBody());
//
//        //THEN
//        assertThat(response.getStatusCode()).isEqualTo(ACCEPTED);
//        assertThat(content.getId()).isEqualTo(travelId);
//        assertThat(content).usingRecursiveComparison().ignoringFields("id").isEqualTo(travelDTOUpdate);
//    }
//
//    @Test
//    @DisplayName("[TravelIntegrationTest] Test de la suppression d'un voyage par son ID")
//    public void testDeleteTravel() {
//
//        //GIVEN
//        int travelId = 1;
//
//        //WHEN
//        ResponseEntity<String> response = testRestTemplate.exchange("/travels/" + travelId, DELETE, null, String.class);
//        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
//
//        ResponseEntity<TravelException> responseException = testRestTemplate.exchange("/travels/" + travelId, GET, null, TravelException.class);
//        TravelException content = Objects.requireNonNull(responseException.getBody());
//
//        //THEN
//        assertThat(responseException.getStatusCode()).isEqualTo(NOT_FOUND);
//        assertThat(content.getMessage()).isEqualTo("Travel not Found");
//    }
//}
