package com.example.Contract;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junit5.PactVerifyProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import io.restassured.RestAssured;

@ExtendWith(PactVerificationInvocationContextProvider.class)
public class UserServiceProviderContractTest {

    @TestTemplate
    @ExtendWith(PactVerificationContext.class)
    void verifyPacts(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("A request for user details")
    public String verifyGetUserDetails() {
        return RestAssured
                .get("http://localhost:8080/users/101")
                .body()
                .asString();
    }
}
