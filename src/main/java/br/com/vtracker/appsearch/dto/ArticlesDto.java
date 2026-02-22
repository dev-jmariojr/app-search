package br.com.vtracker.appsearch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlesDto {

    private String status;
    private Integer totalResults;
    private List<Article> articles;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Article {

        private Source source;
        private String author;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private OffsetDateTime publishedAt;
        private String content;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Source {
        private String id;
        private String name;
    }


}
