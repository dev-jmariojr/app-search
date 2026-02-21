package br.com.vtracker.appsearch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {
    private String status;
    private String code;
    private String message;
}
