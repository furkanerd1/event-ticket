package com.furkanerd.tickets.service;

import com.furkanerd.tickets.model.dto.request.CreateEventRequest;
import com.furkanerd.tickets.model.dto.request.UpdateEventRequest;
import com.furkanerd.tickets.model.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;
import java.util.UUID;

public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest createEventRequest);

    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID eventId, UUID organizerId);

    Event updateEvent(UUID eventId, UUID organizerId, UpdateEventRequest updateEventRequest);

    void deleteEvent(UUID eventId, UUID organizerId);
}
