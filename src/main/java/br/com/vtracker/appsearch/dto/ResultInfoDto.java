package br.com.vtracker.appsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultInfoDto {
    private String font;
    private String subjectSearch;
    private Integer totalResults;
    private LocalDateTime dt;
    private List<ArticleDto> articles;
}
