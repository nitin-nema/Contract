package com.example.Contract;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.core.model.RequestResponsePact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
public class PaymentServiceConsumerContractTest {

    @Pact(consumer = "PaymentService")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("User with ID 101 exists")
                .uponReceiving("A request for user details")
                .path("/users/101")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("id", 101)
                        .stringType("name", "Jane Doe")
                        .decimalType("balance", 150.75))
                .toPact();
    }

    @Test
    public void testGetUserDetails() {
        Response response = RestAssured
                .get("http://localhost:8080/users/101");

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getInt("id"), equalTo(101));
        assertThat(response.jsonPath().getString("name"), equalTo("Jane Doe"));
        assertThat(response.jsonPath().getFloat("balance"), equalTo(150.75f));
    }
}
