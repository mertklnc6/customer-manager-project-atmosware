package com.turkcell.customer_service.core.utilities.exceptions.problemDetails;

public class InternalServerProblemDetails extends ProblemDetails {
    public InternalServerProblemDetails() {
        setTitle("Internal Server Error");
        setDetail("An unexpected error occurred.");
        setType("http://mydomain.com/exceptions/internal");
        setStatus("500");
    }
}
