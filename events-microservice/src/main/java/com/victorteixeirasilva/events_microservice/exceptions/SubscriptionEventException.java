package com.victorteixeirasilva.events_microservice.exceptions;

public class SubscriptionEventException extends RuntimeException {

    public SubscriptionEventException(Throwable cause) {
        super("Não foi possivel cadastrar a subscription no evento.", cause);
    }

    public SubscriptionEventException(String message, Throwable cause) {
        super(message, cause);
    }
}
