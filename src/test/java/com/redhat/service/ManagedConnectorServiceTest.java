package com.redhat.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ManagedConnectorServiceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/connector")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}