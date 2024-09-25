package com.example.Contract;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
public class InvoiceServiceConsumerContractTest {

    @Pact(consumer = "InvoiceService")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Customer with ID 200 is active")
                .uponReceiving("A request for customer details")
                .path("/customers/200")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("id", 200)
                        .stringType("name", "John Doe")
                        .stringType("email", "john.doe@example.com")
                        .stringType("status", "active"))
                .toPact();
    }

    @Test
    public void testGetCustomerDetails() {
        Response response = RestAssured
                .get("http://localhost:8080/customers/200");

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getInt("id"), equalTo(200));
        assertThat(response.jsonPath().getString("name"), equalTo("John Doe"));
        assertThat(response.jsonPath().getString("email"), equalTo("john.doe@example.com"));
        assertThat(response.jsonPath().getString("status"), equalTo("active"));
    }
}
