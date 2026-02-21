package br.com.vtracker.appsearch.controller;

import br.com.vtracker.appsearch.dto.ParamsDto;
import br.com.vtracker.appsearch.dto.ResponseDto;
import br.com.vtracker.appsearch.service.ISearchService;
import br.com.vtracker.appsearch.service.SearchServiceNewsApi;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@RestController
@RequestMapping("/v1")
@Validated
public class SearchController {

    private final ISearchService serviceNewsApi;

    public SearchController(SearchServiceNewsApi serviceNewsApi) {
        this.serviceNewsApi = serviceNewsApi;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public ResponseEntity<ResponseDto> search(
            @Size(min = 3, max = 10, message = "Informe um valor para o 'subject' da pesquisa entre {min} e {max} caracteres")
            @NotNull(message = "Informe o 'subject' para realizar a pesquisa")
            @RequestParam String subject,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false)
            LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(required = false)
            LocalDate endDate)
    {
        var params = new ParamsDto(subject, startDate, endDate);
        return ResponseEntity.ok(serviceNewsApi.searchAnySubject(params));
    }

}
