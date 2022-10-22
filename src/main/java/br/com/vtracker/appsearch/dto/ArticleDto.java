package br.com.vtracker.appsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private LocalDateTime publishedAt;
    private String content;
}
