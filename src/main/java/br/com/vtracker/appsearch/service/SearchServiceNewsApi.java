package br.com.vtracker.appsearch.service;

import br.com.vtracker.appsearch.dto.ResultInfoDto;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceNewsApi implements ISearchService {

    @Value("${app.newsapi.daysFrom}")
    private Integer daysFrom;

    @Value("${app.newsapi.apiKey}")
    private String apiKey;

    private static String urlBase = "https://newsapi.org/v2/everything";

    @Override
    public ResultInfoDto searchAnySubject(String subject) {
        Map<String, String> urlParam = new HashMap<>();

        LocalDate dateFrom = LocalDate.now().minusDays(daysFrom);
        LocalDate dateTo = LocalDate.now();

        URI uri = UriComponentsBuilder
                .fromUriString(urlBase)
                .queryParam("q",subject)
                .queryParam("from", dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .queryParam("to", dateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .queryParam("sortBy", "popularity")
                .queryParam("apiKey", apiKey)
                .buildAndExpand(urlParam)
                .encode(StandardCharsets.UTF_8)
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResultInfoDto> response = restTemplate
                .exchange(
                        URLDecoder.decode(uri.toString(), StandardCharsets.UTF_8),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<ResultInfoDto>(){});

        if(response.getStatusCode().equals(HttpStatus.OK)){
            ResultInfoDto dto = response.getBody();
            dto.setFont(this.getClass().getName());
            dto.setDt(LocalDateTime.now());
            dto.setSubjectSearch(subject);
            return dto;
        }
        else{
            return ResultInfoDto.builder()
                    .font(this.getClass().getName())
                    .totalResults(0)
                    .subjectSearch(subject)
                    .dt(LocalDateTime.now())
                    .build();
        }
    }
}
