package com.victorteixeirasilva.events_microservice.controller;

import com.victorteixeirasilva.events_microservice.domain.dto.SubscriptionRequestDTO;
import com.victorteixeirasilva.events_microservice.domain.dto.request.EventRequestDTO;
import com.victorteixeirasilva.events_microservice.domain.entity.Event;
import com.victorteixeirasilva.events_microservice.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @Operation(
            summary =
                    "Vendo os eventos cadastrados, que ainda não aconteceram",
            description =
                    "Retorna uma lista de todos os eventos cadastrados que tem uma data maior do que a data atual!"
    )
    @GetMapping("/upComming")
    public List<Event> getUpCommingEvents(){
        return eventService.getUpComingEvents();
    }

    @Operation(
            summary =
                    "Criar Evento",
            description =
                    "Retorna o evento criado!"
    )
    @PostMapping
    public Event createEvent(@RequestBody EventRequestDTO eventRequestDTO){
        return eventService.createEvent(eventRequestDTO);
    }

    @Operation(
            summary =
                    "Registrar um participante em um evento",
            description =
                    "Retorna se o participante foi registrado!"
    )
    @PostMapping("/{eventId}/register")
    public ResponseEntity<String> registerParticipant(@PathVariable String eventId, @RequestBody SubscriptionRequestDTO subscriptionRequestDTO){
       eventService.registerParticipant(eventId, subscriptionRequestDTO.participantEmail());
       return ResponseEntity.ok("Participante Registrado Corretamente");
    }


}
