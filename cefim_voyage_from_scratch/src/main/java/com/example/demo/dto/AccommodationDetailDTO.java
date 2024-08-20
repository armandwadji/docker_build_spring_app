package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class AccommodationDetailDTO extends AccommodationDTO{
    @JsonProperty("travel")
    private TravelDTO travelDTO;
}
