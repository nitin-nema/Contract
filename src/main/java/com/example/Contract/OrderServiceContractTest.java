package com.example.Contract;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.core.model.RequestResponsePact;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CustomerService", port = "8080")
public class OrderServiceContractTest {

    @Pact(consumer = "OrderService")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        return builder
                .given("Customer exists")
                .uponReceiving("A request to fetch customer details")
                .path("/customers/123")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("id", 123)
                        .stringType("name", "John Doe")
                        .stringType("email", "johndoe@example.com"))
                .toPact();
    }

    @Test
    public void testGetCustomerDetails() {
        Response response = RestAssured
                .get("http://localhost:8080/customers/123");

        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getInt("id"), equalTo(123));
        assertThat(response.jsonPath().getString("name"), equalTo("John Doe"));
    }
}
