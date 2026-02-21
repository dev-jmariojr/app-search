package br.com.vtracker.appsearch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private String subject;
    private LocalDate startDate;
    private LocalDate endDate;
    private String message;
    private List<ArticlesDto.Article> articles;

}
