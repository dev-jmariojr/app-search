package br.com.vtracker.appsearch.dto;

import java.time.LocalDate;

public record ParamsDto(
    String subject,
    LocalDate startDate,
    LocalDate endDate
) {
}
