package br.com.vtracker.appsearch.service;

import br.com.vtracker.appsearch.dto.ParamsDto;
import br.com.vtracker.appsearch.resource.NewsApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceNewsApiTest {

    @Mock
    private NewsApiClient newsApiClient;

    @Mock
    private ObjectMapper mapper;

    private SearchServiceNewsApi service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        service = new SearchServiceNewsApi(newsApiClient);
        ReflectionTestUtils.setField(
                service,
                "apiKey",
                "1234567890abcdefghijklmnopqrstuvwxyz"
        );
        ReflectionTestUtils.setField(
                service,
                "daysFrom",
                1
        );
    }

    private String mockResponse(HttpStatus status) {
        String jsonResponse = "";
        switch (status) {
            case OK:
                jsonResponse = """                
                        {
                            "status": "ok",
                            "totalResults": 2,
                            "articles": [
                                {
                                    "source": {
                                        "id": "Test",
                                        "name": "Example.com"
                                    },
                                    "author": "Donald Trump",
                                    "title": "Test Article",
                                    "description": "This is a test article.",
                                    "url": "https://example.com/test-article",
                                    "urlToImage": "https://example.com/image.jpg",
                                    "publishedAt": "2024-06-01T12:00:00Z",
                                    "content": "Full content of the test article."
                                },
                                {
                                    "source": {
                                        "id": "Test-2",
                                        "name": "Example.com"
                                    },
                                    "author": "Donald Trump",
                                    "title": "Test Article 2",
                                    "description": "This is a second test article.",
                                    "url": "https://example.com/test-article",
                                    "urlToImage": "https://example.com/image.jpg",
                                    "publishedAt": "2024-07-01T12:00:00Z",
                                    "content": "Full content of the test article."
                                }
                            ]
                        }
                        """;
                break;
            case UNAUTHORIZED:
                jsonResponse = """
                        {
                            "status":"error",
                            "code":"apiKeyInvalid",
                            "message":"Your API key is invalid or incorrect. Check your key, or go to https://newsapi.org to create a free API key."
                        }
                        """;
                break;
            default:
                jsonResponse = """
                        {
                            "return":"desconhecido"
                        }
                        """;
                break;
        }

        return jsonResponse;
    }

    @Test
    void testSearchAnySubjectComSucesso() {
        var mockParams = new ParamsDto("test", LocalDate.now(), LocalDate.now());

        when(newsApiClient.get(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(ResponseEntity.ok(mockResponse(HttpStatus.OK)));

        var response = service.searchAnySubject(mockParams);
        assertNotNull(response);
        assertEquals(mockParams.subject(), response.getSubject());
        assertEquals(mockParams.startDate(), response.getStartDate());
        assertEquals(mockParams.endDate(), response.getEndDate());
        assertFalse(response.getArticles().isEmpty());
    }

    @Test
    void testSearchAnySubjectComErroApiKey() {
        var mockParams = new ParamsDto("test", LocalDate.now(), LocalDate.now());

        when(newsApiClient.get(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(mockResponse(HttpStatus.UNAUTHORIZED)));

        var response = service.searchAnySubject(mockParams);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("Erro ao buscar artigos"));
    }

    @Test
    void testSearchAnySubjectComErroJsonResponse() {
        var mockParams = new ParamsDto("test", LocalDate.now(), LocalDate.now());

        when(newsApiClient.get(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(ResponseEntity.badRequest().body(mockResponse(HttpStatus.BAD_REQUEST)));

        var response = service.searchAnySubject(mockParams);
        assertNotNull(response);
        assertTrue(response.getMessage().contains("Erro ao converter dados"));
    }
}