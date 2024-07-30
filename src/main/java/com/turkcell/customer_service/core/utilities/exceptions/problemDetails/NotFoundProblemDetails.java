package com.turkcell.customer_service.core.utilities.exceptions.problemDetails;

public class NotFoundProblemDetails extends ProblemDetails {
    public NotFoundProblemDetails() {
        setTitle("Not Found");
        setType("http://mydomain.com/exceptions/not-found");
        setStatus("404");
    }
}
