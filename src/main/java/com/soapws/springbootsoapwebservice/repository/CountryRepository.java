package com.soapws.springbootsoapwebservice.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soapws.springbootsoapwebservice.jaxb.Country;

import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
public class CountryRepository {

    private static final ObjectMapper mapper = new ObjectMapper();

    public Optional<Country> findCountryByName(String name) {
        try {
            final List<Country> countries = mapper.readValue(ResourceUtils.getFile("classpath:data/countries.json"), new TypeReference<List<Country>>() {});
            return Optional.ofNullable(countries)
                           .filter(Predicate.not(List::isEmpty))
                           .map(List::stream)
                           .orElse(Stream.empty())
                           .filter(country->country.getName().equalsIgnoreCase(name))
                           .findFirst();
                    
        } catch (IOException e) {
            log.error("UNABLE TO LOAD countries.json file...", e.getMessage());
        }
        return Optional.empty();
    }

}
