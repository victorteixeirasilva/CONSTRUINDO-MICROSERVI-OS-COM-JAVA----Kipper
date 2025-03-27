package com.victorteixeirasilva.events_microservice.domain.dto.request;

public record EventRequestDTO(
        String date,
        int maxParticipantes,
        int registeredParticipantes,
        String title,
        String description
) {
}
