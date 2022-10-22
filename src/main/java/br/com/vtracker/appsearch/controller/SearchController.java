package br.com.vtracker.appsearch.controller;

import br.com.vtracker.appsearch.dto.ResultInfoDto;
import br.com.vtracker.appsearch.service.ISearchService;
import br.com.vtracker.appsearch.service.SearchServiceFakeNews13;
import br.com.vtracker.appsearch.service.SearchServiceFakeNews22;
import br.com.vtracker.appsearch.service.SearchServiceNewsApi;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class SearchController {

    private final ISearchService serviceNewsApi;
    private final ISearchService serviceFakeNews13;
    private final ISearchService serviceFakeNews22;

    public SearchController(SearchServiceNewsApi serviceNewsApi, SearchServiceFakeNews22 serviceFakeNews22, SearchServiceFakeNews13 serviceFakeNews13) {
        this.serviceNewsApi = serviceNewsApi;
        this.serviceFakeNews13 = serviceFakeNews13;
        this.serviceFakeNews22 = serviceFakeNews22;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ResultInfoDto> search(@RequestParam String value){

        List<ResultInfoDto> list = new ArrayList<>();
        list.add(serviceFakeNews13.searchAnySubject(value));
        list.add(serviceFakeNews22.searchAnySubject(value));
        list.add(serviceNewsApi.searchAnySubject(value));

        return list;
    }

}
