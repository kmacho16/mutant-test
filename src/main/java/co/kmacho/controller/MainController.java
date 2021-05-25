package co.kmacho.controller;

import co.kmacho.services.MutantService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;
import java.util.Map;


@Controller("/api")
public class MainController {

    @Inject
    MutantService mutantService;

    @Post("mutant")
    public HttpResponse<Boolean> receiveData(String[] dna){
        Boolean isMutant =  mutantService.isMutant(dna);
        if (isMutant){
            return HttpResponse.status(HttpStatus.OK).body(isMutant);
        }
        return HttpResponse.status(HttpStatus.FORBIDDEN).body(isMutant);
    }

    @Get("stats")
    public HttpResponse<Map<String, String>> stats(){
        return HttpResponse.status(HttpStatus.OK).body(mutantService.stats());
    }
}
