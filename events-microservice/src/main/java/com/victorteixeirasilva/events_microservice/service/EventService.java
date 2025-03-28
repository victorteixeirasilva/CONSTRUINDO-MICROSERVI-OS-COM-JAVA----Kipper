package com.victorteixeirasilva.events_microservice.service;

import com.victorteixeirasilva.events_microservice.domain.dto.request.EmailRequestDTO;
import com.victorteixeirasilva.events_microservice.domain.dto.request.EventRequestDTO;
import com.victorteixeirasilva.events_microservice.domain.entity.Event;
import com.victorteixeirasilva.events_microservice.domain.entity.Subscription;
import com.victorteixeirasilva.events_microservice.exceptions.EventFullException;
import com.victorteixeirasilva.events_microservice.exceptions.EventNotFoundException;
import com.victorteixeirasilva.events_microservice.exceptions.EventsIsEmptyException;
import com.victorteixeirasilva.events_microservice.exceptions.SubscriptionEventException;
import com.victorteixeirasilva.events_microservice.repository.EventRepository;
import com.victorteixeirasilva.events_microservice.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired EmailServiceClient emailServiceClient;

    public List<Event> getAllEvents(){
        if (eventRepository.findAll().isEmpty()){
            throw new EventsIsEmptyException();
        }
        return eventRepository.findAll();
    }

    public List<Event> getUpComingEvents() {
        if (eventRepository.findUpcomingEvents(LocalDateTime.now()).isEmpty()) {
            throw new EventsIsEmptyException("Não existe nenhum evento que ainda esteja para acontecer!");
        }
        return eventRepository.findUpcomingEvents(LocalDateTime.now());
    }

    public Event createEvent(EventRequestDTO eventRequestDTO) {
        Event newEvent = new Event(eventRequestDTO);
        try {
            return eventRepository.save(newEvent);
        } catch (Exception e) {
            throw new InternalError("Não foi possível cadastrar esse evento no banco de dados!", e.getCause());
        }
    }

    private Boolean isEventFull(Event event){
        return event.getRegisteredParticipantes() >= event.getMaxParticipantes();
    }

    public void registerParticipant(String eventId, String participantEmail){
        Event event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);

        if (isEventFull(event)) {
            throw new EventFullException();
        }

        try {
            Subscription subscription = new Subscription(event, participantEmail);
            subscriptionRepository.save(subscription);
        } catch (Exception e) {
            throw new SubscriptionEventException(e.getCause());
        }

        event.setRegisteredParticipantes(event.getRegisteredParticipantes() + 1);

        EmailRequestDTO emailRequestDTO = new EmailRequestDTO(participantEmail, "Confirmação de Incrição",  "Você foi inscrito no evento com sucesso!");

        try {
            emailServiceClient.sendEmail(emailRequestDTO);
        } catch (Exception e) {
            throw new InternalError("Não foi possivel enviar o e-mail", e.getCause());
        }
    }

}
