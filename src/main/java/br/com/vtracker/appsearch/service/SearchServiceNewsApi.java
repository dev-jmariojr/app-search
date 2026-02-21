package br.com.vtracker.appsearch.service;

import br.com.vtracker.appsearch.dto.ArticlesDto;
import br.com.vtracker.appsearch.dto.ErrorDto;
import br.com.vtracker.appsearch.dto.ParamsDto;
import br.com.vtracker.appsearch.dto.ResponseDto;
import br.com.vtracker.appsearch.resource.NewsApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class SearchServiceNewsApi implements ISearchService {

    @Value("${app.newsapi.daysFrom}")
    private Integer daysFrom;

    @Value("${app.newsapi.apiKey}")
    private String apiKey;

    private static final DateTimeFormatter DATEFORMAT_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String SORT_BY  = "popularity";

    private final NewsApiClient newsApiClient;
    private final ObjectMapper mapper;

    public SearchServiceNewsApi(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
        this.mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public ResponseDto searchAnySubject(ParamsDto params) {
        LocalDate fromDate = params.startDate() != null ? params.startDate() : LocalDate.now().minusDays(daysFrom);
        LocalDate toDate = params.endDate() != null ? params.endDate() : LocalDate.now();

        var result = newsApiClient.get(
                params.subject(),
                fromDate.format(DATEFORMAT_PATTERN),
                toDate.format(DATEFORMAT_PATTERN),
                SORT_BY,
                apiKey);

        return tratarResponse(params, result);
    }

    private <T> T parse(String body, TypeReference<T> type) throws JsonProcessingException {
        return mapper.readValue(body, type);
    }

    private ResponseDto tratarResponse(ParamsDto params, ResponseEntity<String> response) {
        var result = ResponseDto.builder()
                .subject(params.subject())
                .startDate(params.startDate())
                .endDate(params.endDate())
                .build();

        try {
            var body = response.getBody();
            if (response.getStatusCode() == HttpStatus.OK) {
                var articles = parse(body, new TypeReference<ArticlesDto>(){});
                result.setArticles(articles.getArticles());
                result.setMessage(String.format("Artigos encontrados: %d", articles.getTotalResults()));
            }
            else {
                var error = parse(body, new TypeReference<ErrorDto>(){});
                result.setMessage(String.format("Erro ao buscar artigos: %s", error.getMessage()));
            }
        } catch (JsonProcessingException e) {
            result.setMessage(String.format("Erro ao converter dados: %s", e.getMessage()));
        }

        return result;
    }
}
