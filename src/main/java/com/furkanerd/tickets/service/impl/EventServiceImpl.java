package com.furkanerd.tickets.service.impl;

import com.furkanerd.tickets.exception.EventNotFoundException;
import com.furkanerd.tickets.exception.EventUpdateException;
import com.furkanerd.tickets.exception.TicketTypeNotFoundException;
import com.furkanerd.tickets.exception.UserNotFoundException;
import com.furkanerd.tickets.model.dto.request.CreateEventRequest;
import com.furkanerd.tickets.model.dto.request.UpdateEventRequest;
import com.furkanerd.tickets.model.dto.request.UpdateTicketTypeRequest;
import com.furkanerd.tickets.model.entity.Event;
import com.furkanerd.tickets.model.entity.TicketType;
import com.furkanerd.tickets.model.entity.User;
import com.furkanerd.tickets.model.enums.EventStatusEnum;
import com.furkanerd.tickets.repository.EventRepository;
import com.furkanerd.tickets.repository.UserRepository;
import com.furkanerd.tickets.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest createEventRequest) {
        User organizer = userRepository.findById(organizerId).orElseThrow(() -> new UserNotFoundException("User not found with ID "+organizerId));

        Event toCreate = new Event();

        List<TicketType> ticketTypeList = createEventRequest.ticketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.name());
                    ticketTypeToCreate.setPrice(ticketType.price());
                    ticketTypeToCreate.setDescription(ticketType.description());
                    ticketTypeToCreate.setTotalAvailable(ticketType.totalAvailable());
                    ticketTypeToCreate.setEvent(toCreate);
                    return ticketTypeToCreate;
                }
        ).toList();


        toCreate.setName(createEventRequest.name());
        toCreate.setStart(createEventRequest.start());
        toCreate.setEnd(createEventRequest.end());
        toCreate.setVenue(createEventRequest.venue());
        toCreate.setSalesStartDate(createEventRequest.salesStartDate());
        toCreate.setSalesEndDate(createEventRequest.salesEndDate());
        toCreate.setStatus(createEventRequest.status());
        toCreate.setOrganizer(organizer);
        toCreate.setTicketTypes(ticketTypeList);

        return eventRepository.save(toCreate);
    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId,pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID eventId, UUID organizerId) {
        return eventRepository.findByIdAndOrganizerId(eventId,organizerId);
    }

    @Override
    @Transactional
    public Event updateEvent(UUID eventId, UUID organizerId, UpdateEventRequest updateEventRequest) {
        if(updateEventRequest.id() == null) throw new EventUpdateException("Event ID cannot be null");

        if(!eventId.equals(updateEventRequest.id())) throw new EventUpdateException("You cannot update the ID of event");

        Event existingEvent = eventRepository.findByIdAndOrganizerId(eventId,organizerId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID "+eventId));

        existingEvent.setName(updateEventRequest.name());
        existingEvent.setStart(updateEventRequest.start());
        existingEvent.setEnd(updateEventRequest.end());
        existingEvent.setVenue(updateEventRequest.venue());
        existingEvent.setSalesStartDate(updateEventRequest.salesStartDate());
        existingEvent.setSalesEndDate(updateEventRequest.salesEndDate());
        existingEvent.setStatus(updateEventRequest.status());

        Set<UUID> requestTicketTypeIds = updateEventRequest.ticketTypes().stream()
                .map(UpdateTicketTypeRequest::id) // req -> req.id()
                .filter(Objects::nonNull) // id -> id != null
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(ticketTypeId -> !requestTicketTypeIds.contains(ticketTypeId.getId()));

        Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for (UpdateTicketTypeRequest requestTicketType : updateEventRequest.ticketTypes()) {
            if(requestTicketType.id() == null) {

                TicketType ticketType = new TicketType();
                ticketType.setName(requestTicketType.name());
                ticketType.setPrice(requestTicketType.price());
                ticketType.setDescription(requestTicketType.description());
                ticketType.setTotalAvailable(requestTicketType.totalAvailable());
                ticketType.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketType);

            }else if (existingTicketTypesIndex.containsKey(requestTicketType.id())) {
                TicketType toUpdate = existingTicketTypesIndex.get(requestTicketType.id());
                toUpdate.setName(requestTicketType.name());
                toUpdate.setPrice(requestTicketType.price());
                toUpdate.setDescription(requestTicketType.description());
                toUpdate.setTotalAvailable(requestTicketType.totalAvailable());
            }else {
                throw new TicketTypeNotFoundException("TicketTypeService not found with ID" + requestTicketType.id());
            }
        }
        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(UUID eventId, UUID organizerId) {
       Event eventToDelete =  eventRepository.findByIdAndOrganizerId(eventId,organizerId).orElseThrow(() -> new EventNotFoundException("Event not found with ID "+eventId));
       eventRepository.delete(eventToDelete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED,pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchPublishedEvents(query,pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID eventId) {
        return eventRepository.findByIdAndStatus(eventId, EventStatusEnum.PUBLISHED);
    }
}
