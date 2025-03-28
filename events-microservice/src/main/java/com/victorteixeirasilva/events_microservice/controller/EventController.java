package com.victorteixeirasilva.events_microservice.controller;

import com.victorteixeirasilva.events_microservice.domain.dto.SubscriptionRequestDTO;
import com.victorteixeirasilva.events_microservice.domain.dto.request.EventRequestDTO;
import com.victorteixeirasilva.events_microservice.domain.entity.Event;
import com.victorteixeirasilva.events_microservice.exceptions.EventFullException;
import com.victorteixeirasilva.events_microservice.exceptions.EventsIsEmptyException;
import com.victorteixeirasilva.events_microservice.exceptions.SubscriptionEventException;
import com.victorteixeirasilva.events_microservice.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Eventos", description = "Gerenciador de Enventos")
@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @Operation(
            summary =
                    "Vendo todos os eventos cadastrados!",
            description =
                    "Retorna uma lista de todos os eventos cadastrados!"
    )
    @GetMapping
    public ResponseEntity getAllEvents(){
        try {
            return ResponseEntity.ok(eventService.getAllEvents());
        } catch (EventsIsEmptyException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary =
                    "Vendo os eventos cadastrados, que ainda não aconteceram",
            description =
                    "Retorna uma lista de todos os eventos cadastrados que tem uma data maior do que a data atual!"
    )
    @GetMapping("/upComming")
    public ResponseEntity getUpCommingEvents(){
        try {
            return ResponseEntity.ok(eventService.getUpComingEvents());
        } catch (EventsIsEmptyException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(
            summary =
                    "Criar Evento",
            description =
                    "Retorna o evento criado!"
    )
    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventRequestDTO eventRequestDTO){
        try {
            return ResponseEntity.ok(eventService.createEvent(eventRequestDTO));
        } catch (InternalError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(
            summary =
                    "Registrar um participante em um evento",
            description =
                    "Retorna se o participante foi registrado!"
    )
    @PostMapping("/{eventId}/register")
    public ResponseEntity registerParticipant(@PathVariable String eventId, @RequestBody SubscriptionRequestDTO subscriptionRequestDTO){
        try {
            eventService.registerParticipant(eventId, subscriptionRequestDTO.participantEmail());
            return ResponseEntity.ok("Participante Registrado Corretamente");
        } catch (EventFullException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (SubscriptionEventException | InternalError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}
