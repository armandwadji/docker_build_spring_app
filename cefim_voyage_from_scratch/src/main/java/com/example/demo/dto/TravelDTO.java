package com.example.demo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class TravelDTO {

    private int id;

    @NotBlank(message = "The name of a trip cannot be empty.")
    @NotNull(message = "The name of a trip cannot be null.")
    private String title;

    private String description;

    @NotBlank(message = "The place of a journey cannot be empty.")
    @NotNull(message = "The place of a journey cannot be null.")
    private String location;

    @NotNull(message = "The start date of a trip cannot be null.")
    private LocalDate start;

    @NotNull(message = "The end date of a trip cannot be null.")
    private LocalDate end;
}
