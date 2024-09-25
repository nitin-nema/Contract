package com.example.Contract;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junit5.PactVerifyProvider;
import io.restassured.RestAssured;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(PactVerificationInvocationContextProvider.class)
public class CustomerServiceProviderContractTestVerification {

    @TestTemplate
    @ExtendWith(PactVerificationContext.class)
    void verifyPacts(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("A request for customer details")
    public String verifyGetCustomerDetails() {
        return RestAssured
                .get("http://localhost:8080/customers/200")
                .body()
                .asString();
    }
}
