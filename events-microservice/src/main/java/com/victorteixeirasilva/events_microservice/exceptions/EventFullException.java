package com.victorteixeirasilva.events_microservice.exceptions;

public class EventFullException extends RuntimeException {

    public EventFullException() {
        super("Evento está cheio");
    }

    public EventFullException(String message) {
        super(message);
    }
}
