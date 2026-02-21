package br.com.vtracker.appsearch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ArticlesDto {

    private String status;
    private Integer totalResults;
    private List<Article> articles;

    @Data
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Source {
        private String id;
        private String name;
    }


}
