package br.com.vtracker.appsearch.controller;

import br.com.vtracker.appsearch.dto.ArticlesDto;
import br.com.vtracker.appsearch.dto.ParamsDto;
import br.com.vtracker.appsearch.dto.ResponseDto;
import br.com.vtracker.appsearch.service.SearchServiceNewsApi;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchControllerTest {

    @InjectMocks
    private SearchController controller;

    @Mock
    private SearchServiceNewsApi serviceNewsApi;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RestAssuredMockMvc.standaloneSetup(controller);
    }

    private ResponseDto responseMock(String subject, LocalDate startDate, LocalDate endDate, boolean error) {
        if (error) {
            return ResponseDto.builder()
                    .message("Erro ao buscar artigos")
                    .subject(subject)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
        }
        else {
            return ResponseDto.builder()
                    .message("Artigos encontrados: 1")
                    .subject(subject)
                    .startDate(startDate)
                    .endDate(endDate)
                    .articles(new ArrayList<>(){
                        {
                            add(ArticlesDto.Article.builder()
                                    .url("https://example.com/test")
                                    .urlToImage("https://example.com/test.jpg")
                                    .title("Test title")
                                    .description("Test description")
                                    .author("Test")
                                    .publishedAt(OffsetDateTime.now())
                                    .content("Test content")
                                    .source(ArticlesDto.Source.builder().id("Test").name("Example.com").build())
                                    .build()
                            );
                        }
                    })
                    .build();
        }
    }

    @Test
    void testSearchComSucesso() {
        var subject = "test";
        var startDate = LocalDate.now();
        var endDate = LocalDate.now();
        when(serviceNewsApi.searchAnySubject(any(ParamsDto.class))).thenReturn(responseMock(subject, startDate, endDate, false));
        var response = controller.search("test", startDate, endDate);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).getArticles().isEmpty());
    }

    @Test
    void testSearchComErro() {
        var subject = "test error";
        when(serviceNewsApi.searchAnySubject(any(ParamsDto.class))).thenReturn(responseMock(subject, null, null, true));
        var response = controller.search(subject, null, null);
        assertNotNull(response);
        assertNull(Objects.requireNonNull(response.getBody()).getArticles());
    }

    @Test
    void testSearchComErroNotFound() {
        String invalidUrl = "/v1/searchx";
        given().contentType(ContentType.JSON).when().get(invalidUrl + "?subject=Test").then().status(HttpStatus.NOT_FOUND);
    }

    @Test
    void testSearchComErroBadRequestSubjectNull() {
        String validUrl = "/v1/search";
        given().contentType(ContentType.JSON)
                .when().get(validUrl + "?startDate=2024-01-01&endDate=2024-01-31")
                .then().status(HttpStatus.BAD_REQUEST);
    }

}
