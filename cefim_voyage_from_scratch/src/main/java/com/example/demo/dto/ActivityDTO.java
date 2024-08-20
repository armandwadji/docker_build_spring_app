package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @NotBlank
    private LocalDateTime start;

    @NotNull
    @NotBlank
    private LocalDateTime end;

    @NotNull
    @NotBlank
    private int price;

    private String image;

}
