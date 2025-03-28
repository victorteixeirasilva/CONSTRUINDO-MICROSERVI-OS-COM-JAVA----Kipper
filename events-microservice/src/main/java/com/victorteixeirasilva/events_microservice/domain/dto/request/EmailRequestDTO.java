package com.victorteixeirasilva.events_microservice.domain.dto.request;

public record EmailRequestDTO(String to, String subject, String body) {
}
