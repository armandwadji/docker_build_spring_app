package com.example.demo.unit.controller;

import com.example.demo.controller.TravelController;
import com.example.demo.dto.TravelDTO;
import com.example.demo.service.TravelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.util.List;
import java.util.Locale;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ActiveProfiles("test")
@WebMvcTest(TravelController.class)
public class TravelControllerTest {

    private static final Faker FAKER = new Faker(new Locale("fr"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TravelService travelService;

    List<TravelDTO> travelDTOList;

    @BeforeEach
    void setUp() {
        int travelId = 1;
        this.travelDTOList = List.of(
            TravelDTO.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build(),
            TravelDTO.builder().id(travelId + 1).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build(),
            TravelDTO.builder().id(travelId + 2).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build()
        );
    }

    @Test
    @DisplayName("[TravelController] Test de récupération de la liste des voyages")
    public void testGetAllTravels() throws Exception {
        //GIVEN
        Mockito.when(travelService.list()).thenReturn(travelDTOList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/travels");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        //WHEN
        String bodyContent = mockMvc.perform(requestBuilder).andExpect(resultStatus)
                .andReturn()
                .getResponse()
                .getContentAsString();

        //THEN
        List<TravelDTO> result = List.of(objectMapper.readValue(bodyContent, TravelDTO[].class));
        Assertions.assertEquals(travelDTOList.size(), result.size());
    }

    @Test
    @DisplayName("[TravelController] Test de récupération d'un voyage par son ID")
    public void testGetTravelById() throws Exception {
        //GIVEN
        int travelId = 1;
        Mockito.when(travelService.read(travelId)).thenReturn(travelDTOList.get(0));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/travels/{id}" , travelId);
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

        //WHEN THEN
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(travelDTOList.get(0))));
    }

    @Test
    @DisplayName("[TravelController] Test de création d'un voyage")
    public void testPostTravel() throws Exception {
        //GIVEN
        int travelId = 1;
        TravelDTO travelDTO = travelDTOList.get(0);
        TravelDTO expected = TravelDTO.builder().id(travelId).title(travelDTO.getTitle()).description(travelDTO.getDescription()).location(travelDTO.getLocation()).start(travelDTO.getStart()).end(travelDTO.getEnd()).build();
        Mockito.when(travelService.create(Mockito.any())).thenReturn(expected);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/travels")
                .contentType(APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(travelDTO));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();

        //WHEN THEN
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)));

    }

    @Test
    @DisplayName("[TravelController] Test de l'éditons d'un voyage par son ID")
    public void testUpdateTravel() throws Exception {
        //GIVEN
        int travelId = 1;
        TravelDTO travelDTO = travelDTOList.get(0);
        TravelDTO expected = TravelDTO.builder().id(travelId).title(FAKER.name().title()).description(FAKER.lorem().sentence(10)).location(FAKER.address().fullAddress()).start(LocalDate.now()).end(LocalDate.now().plusDays((long) (Math.random() * 10))).build();
        Mockito.when(travelService.update(Mockito.eq(travelId),Mockito.any())).thenReturn(expected);

        //WHEN
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/travels/{id}" , travelId)
                .header("content-type", APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(travelDTO));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isAccepted();

        //THEN
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expected)));

    }

    @Test
    @DisplayName("[TravelController] Test de la suppression d'un voyage par son ID")
    public void testDeleteTravel() throws Exception {
        //GIVEN
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/travels/1");
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isNoContent();

        //WHEN THEN
        mockMvc.perform(requestBuilder)
                .andExpect(resultStatus);

    }
}
