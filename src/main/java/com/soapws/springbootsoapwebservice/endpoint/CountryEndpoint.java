package com.soapws.springbootsoapwebservice.endpoint;

import com.soapws.springbootsoapwebservice.confg.SoapWSConfiguration;
import com.soapws.springbootsoapwebservice.jaxb.GetCountryRequest;
import com.soapws.springbootsoapwebservice.jaxb.GetCountryResponse;
import com.soapws.springbootsoapwebservice.jaxb.ObjectFactory;
import com.soapws.springbootsoapwebservice.repository.CountryRepository;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Endpoint
public class CountryEndpoint {
    
    private final CountryRepository countryRepository;

    @PayloadRoot(namespace = SoapWSConfiguration.NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        final ObjectFactory factory = new ObjectFactory();
        final GetCountryResponse response = factory.createGetCountryResponse();
        
        countryRepository.findCountryByName(request.getName())
                         .ifPresent(response::setCountry);
        return response;                             
    }
}
