package com.example.Contract;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junit5.PactVerifyProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import static io.restassured.RestAssured.get;

@ExtendWith(PactVerificationInvocationContextProvider.class)
public class CustomerServiceContractTest {

    @TestTemplate
    @ExtendWith(PactVerificationContext.class)
    void verifyPacts(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("A request to fetch customer details")
    public String verifyGetCustomerDetails() {
        return get("http://localhost:8080/customers/123").body().asString();
    }
}
