package br.com.vtracker.appsearch.resource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "newsApiClient",
        url = "https://newsapi.org"
)
public interface NewsApiClient {

    @GetMapping("/v2/everything")
    ResponseEntity<String> get(
            @RequestParam("q") String query,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("apiKey") String apiKey
    );
}