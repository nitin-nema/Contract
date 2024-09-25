package com.example.Contract;

@Pact(consumer = "PaymentService")
public RequestResponsePactPayment createEvolvedPact(PactDslWithProvider builder) {
    return builder
            .given("User with ID 101 exists")
            .uponReceiving("A request for user details with email")
            .path("/users/101")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(new PactDslJsonBody()
                    .integerType("id", 101)
                    .stringType("name", "Jane Doe")
                    .decimalType("balance", 150.75)
                    .stringType("email", "jane.doe@example.com"))
            .toPact();
}
