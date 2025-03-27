package com.victorteixeirasilva.events_microservice.domain.entity;

import com.sun.jdi.request.EventRequest;
import com.victorteixeirasilva.events_microservice.domain.dto.request.EventRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "event")
@Table(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private int maxParticipantes;
    private int registeredParticipantes;
    private String date;
    private String title;
    private String description;

    public Event(EventRequestDTO eventRequestDTO){
        this.date = eventRequestDTO.date();
        this.maxParticipantes = eventRequestDTO.maxParticipantes();
        this.registeredParticipantes = eventRequestDTO.registeredParticipantes();
        this.title = eventRequestDTO.title();
        this.description = eventRequestDTO.description();
    }

}
