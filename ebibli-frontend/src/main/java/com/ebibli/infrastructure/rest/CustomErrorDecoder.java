package com.ebibli.infrastructure.rest;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.web.client.RestClientException;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        if(response.status() == 500) {
            return new RestClientException("Une erreur est survenue !");
        }
        if(response.status() == 404) {
            return new RestClientException("Pas trouvé !");
        }
        if(response.status() == 400) {
            return new RestClientException(response.body().toString());
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}
