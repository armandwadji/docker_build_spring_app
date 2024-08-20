package com.example.demo.unit.controller;

import com.example.demo.controller.AccommodationController;
import com.example.demo.dto.AccommodationDTO;
import com.example.demo.dto.AccommodationDetailDTO;
import com.example.demo.dto.TravelDTO;
import com.example.demo.service.AccommodationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("test")
@WebMvcTest(AccommodationController.class)
class AccommodationControllerTest {

    private static final Faker FAKER = new Faker(new Locale("fr"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    AccommodationService accommodationService;

    List<AccommodationDTO> accommodationDTOList;

    @BeforeEach
    void setUp() {
        long accommodationId = 1L;
        accommodationDTOList = List.of(
                AccommodationDTO.builder().id(accommodationId).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build(),
                AccommodationDTO.builder().id(accommodationId + 1).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build(),
                AccommodationDTO.builder().id(accommodationId + 2).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build()
        );
    }

    @Test
    @DisplayName("[AccommodationController] Test de création d'un logement")
    void testPostAccommodation() throws Exception {
        //GIVEN
        long accommodationId = 1L;
        AccommodationDTO accommodationDTO = accommodationDTOList.get(0);
        AccommodationDTO expected = AccommodationDTO.builder().id(accommodationId).name(accommodationDTO.getName()).address(accommodationDTO.getAddress()).phone(accommodationDTO.getPhone()).price(accommodationDTO.getPrice()).start(accommodationDTO.getStart()).end(accommodationDTO.getEnd()).build();
        Mockito.when(accommodationService.create(Mockito.any())).thenReturn(expected);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/accommodations")
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(accommodationDTO));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();

        //WHEN THEN
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)));

    }

    @Test
    @DisplayName("[AccommodationController] Test de récupération de la liste des logements")
    void testGetAllAccommodations() throws Exception {
        //GIVEN
        Mockito.when(accommodationService.list()).thenReturn(accommodationDTOList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accommodations");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        //WHEN
        String bodyContent = mockMvc.perform(requestBuilder).andExpect(resultStatus)
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<AccommodationDTO> result = List.of(objectMapper.readValue(bodyContent, AccommodationDTO[].class));
        Assertions.assertEquals(accommodationDTOList.size(), result.size());
    }

    @Test
    @DisplayName("[AccommodationController] Test de récupération d'un logement par son ID")
    void read() throws Exception {
        //GIVEN
        long accommodationId = 1L;

        TravelDTO travelDTO = TravelDTO.builder().id(1).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        AccommodationDetailDTO accommodationDetailDTO = new AccommodationDetailDTO();
        BeanUtils.copyProperties(accommodationDTOList.get(0), accommodationDetailDTO);
        accommodationDetailDTO.setTravelDTO(travelDTO);

        Mockito.when(accommodationService.read((int) accommodationId)).thenReturn(accommodationDetailDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/accommodations/{id}" , accommodationId);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        //WHEN THEN
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(accommodationDetailDTO)));
    }

    @Test
    @DisplayName("[AccommodationController] Test de l'éditons d'un logement par son ID")
    void testUpdateAccommodation() throws Exception {
        //GIVEN
        long accommodationId = 1L;
        AccommodationDTO accommodationDTO = accommodationDTOList.get(0);
        AccommodationDTO expected = AccommodationDTO.builder().id(accommodationId).name(FAKER.name().title()).address(FAKER.address().fullAddress()).phone(FAKER.phoneNumber().cellPhone()).price((50 + (int) (Math.random() * 2000)) * 100).start(LocalDateTime.now()).end(LocalDateTime.now().plusDays((long) (Math.random() * 10))).build();
        Mockito.when(accommodationService.update( Mockito.eq((int)accommodationId),Mockito.any())).thenReturn(expected);

        //WHEN
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/accommodations/{id}" , accommodationId)
                .header("content-type", APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(accommodationDTO));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isAccepted();

        //THEN
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)));

    }

    @Test
    @DisplayName("[AccommodationController] Test de la suppression d'un logement par son ID")
    void testDeleteAccommodation() throws Exception {
        //GIVEN
        long accommodationId = 1L;
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/accommodations/{id}" , accommodationId);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isNoContent();

        //WHEN THEN
        mockMvc.perform(requestBuilder)
                .andExpect(resultStatus);
    }
}
