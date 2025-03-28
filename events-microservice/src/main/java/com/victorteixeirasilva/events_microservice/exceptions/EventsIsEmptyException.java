package com.victorteixeirasilva.events_microservice.exceptions;

public class EventsIsEmptyException extends RuntimeException {
    public EventsIsEmptyException(){
        super("Não Existe nenhum evento cadastrado!");
    }

    public EventsIsEmptyException(String message) {
        super(message);
    }
}
