package com.ebibli.infrastructure.rest;

import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import feign.Response;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestClientException;

import java.nio.charset.Charset;
import java.util.HashMap;

public class CustomErrorDecoderTest {

    CustomErrorDecoder customErrorDecoder = new CustomErrorDecoder();

    @Test
    public void testShouldReturn400() throws IllegalAccessException, InstantiationException {

        String errorResponse = "error 400";
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), Request.Body.empty(), RequestTemplate.class.newInstance());
        Response response = Response
                .builder()
                .request(request)
                .status(400)
                .body(errorResponse, Charset.defaultCharset())
                .headers(new HashMap<>())
                .build();

        Exception exception = customErrorDecoder.decode("methodKey", response);

        // then
        Assertions.assertThat(exception).isInstanceOf(RestClientException.class);
        Assert.assertEquals("error 400", exception.getMessage());
    }

    @Test
    public void testShouldReturn500() throws IllegalAccessException, InstantiationException {

        String errorResponse = "error 500";
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), Request.Body.empty(), RequestTemplate.class.newInstance());
        Response response = Response
                .builder()
                .request(request)
                .status(500)
                .body(errorResponse, Charset.defaultCharset())
                .headers(new HashMap<>())
                .build();

        Exception exception = customErrorDecoder.decode("methodKey", response);

        // then
        Assertions.assertThat(exception).isInstanceOf(RestClientException.class);
        Assert.assertEquals("error 500", exception.getMessage());
    }

    @Test
    public void testShouldReturn404() throws IllegalAccessException, InstantiationException {

        String errorResponse = "error 404";
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), Request.Body.empty(), RequestTemplate.class.newInstance());
        Response response = Response
                .builder()
                .request(request)
                .status(404)
                .body(errorResponse, Charset.defaultCharset())
                .headers(new HashMap<>())
                .build();

        Exception exception = customErrorDecoder.decode("methodKey", response);

        // then
        Assertions.assertThat(exception).isInstanceOf(RestClientException.class);
        Assert.assertEquals("error 404", exception.getMessage());
    }

    @Test
    public void testShouldReturn456() throws IllegalAccessException, InstantiationException {

        String errorResponse = "error 456";
        Request request = Request.create(Request.HttpMethod.GET, "url", new HashMap<>(), Request.Body.empty(), RequestTemplate.class.newInstance());
        Response response = Response
                .builder()
                .request(request)
                .status(456)
                .body(errorResponse, Charset.defaultCharset())
                .headers(new HashMap<>())
                .build();

        Exception exception = customErrorDecoder.decode("methodKey", response);

        // then
        Assertions.assertThat(exception).isInstanceOf(FeignException.FeignClientException.class);
        Assert.assertTrue(exception.getMessage().contains("error 456"));
    }
}