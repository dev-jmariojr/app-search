package br.com.vtracker.appsearch.service;

import br.com.vtracker.appsearch.dto.ArticleDto;
import br.com.vtracker.appsearch.dto.ResultInfoDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
@Service
public class SearchServiceFakeNews13 implements ISearchService {
    @Override
    public ResultInfoDto searchAnySubject(String subject) {
        return ResultInfoDto.builder()
                .font(this.getClass().getName())
                .subjectSearch(subject)
                .dt(LocalDateTime.now())
                .totalResults(1)
                .articles(
                        Arrays.asList(
                                ArticleDto.builder()
                                        .author("Luciano Hang")
                                        .content("Fakenews number two: " + subject)
                                        .build()
                        )
                )
                .build();
    }
}
