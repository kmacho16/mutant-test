package co.kmacho.controller;

import co.kmacho.services.MutantService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import javax.inject.Inject;


@Controller("/api")
public class MainController {

    @Inject
    MutantService mutantService;

    @Post("receive")
    public Boolean receiveData(String[] dna){
        return mutantService.isMutant(dna);
    }
}
