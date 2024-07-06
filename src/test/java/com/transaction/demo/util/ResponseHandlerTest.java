package com.transaction.demo.util;

import com.transaction.demo.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandlerTest {

    ///////////////// generateResponse /////////////////////////////////////////////
    @Test
    public void testGenerateResponse() {
        ResponseEntity<Response<String>> response = ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, "Test msg", "data");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals("Test msg", response.getBody().getMessage());
        Assertions.assertEquals("data", response.getBody().getData());
        Assertions.assertNotNull(response.getBody().getTimeStamp());
    }

    ///////////////// generateSuccess /////////////////////////////////////////////

    @Test
    public void testGenerateSuccess() {
        ResponseEntity<Response<String>> response = ResponseHandler.generateSuccess("Test msg", "data");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Test msg", response.getBody().getMessage());
        Assertions.assertEquals("data", response.getBody().getData());
        Assertions.assertNotNull(response.getBody().getTimeStamp());
    }

    ///////////////// generateFailure /////////////////////////////////////////////

    @Test
    public void testGenerateFailure() {
        ResponseEntity<Response<String>> response = ResponseHandler.generateError("Test msg", "data");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Test msg", response.getBody().getMessage());
        Assertions.assertEquals("data", response.getBody().getData());
        Assertions.assertNotNull(response.getBody().getTimeStamp());
    }
}
