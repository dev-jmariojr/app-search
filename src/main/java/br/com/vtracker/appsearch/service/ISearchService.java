package br.com.vtracker.appsearch.service;

import br.com.vtracker.appsearch.dto.ParamsDto;
import br.com.vtracker.appsearch.dto.ResponseDto;

public interface ISearchService {

    ResponseDto searchAnySubject(ParamsDto subject);

}
