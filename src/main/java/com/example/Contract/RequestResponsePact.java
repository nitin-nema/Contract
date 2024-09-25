package com.example.Contract;

@Pact(consumer = "OrderService")
public RequestResponsePact createEvolvedPact(PactDslWithProvider builder) {
    return builder
            .given("Customer with ID 123 exists")
            .uponReceiving("A request for customer details including address")
            .path("/customers/123")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(new PactDslJsonBody()
                    .integerType("id", 123)
                    .stringType("name", "John Doe")
                    .stringType("email", "john.doe@example.com")
                    .stringType("address", "123 Elm Street"))
            .toPact();
}
