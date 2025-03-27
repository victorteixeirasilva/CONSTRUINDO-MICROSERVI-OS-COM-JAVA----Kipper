package com.victorteixeirasilva.events_microservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "subscription")
@Table(name = "subscription")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    private String participanteEmail;

    public Subscription(Event event, String participanteEmail) {
        this.event = event;
        this.participanteEmail = participanteEmail;
    }
}
