package com.furkanerd.tickets.service.impl;

import com.furkanerd.tickets.exception.UserNotFoundException;
import com.furkanerd.tickets.model.dto.request.CreateEventRequest;
import com.furkanerd.tickets.model.entity.Event;
import com.furkanerd.tickets.model.entity.TicketType;
import com.furkanerd.tickets.model.entity.User;
import com.furkanerd.tickets.repository.EventRepository;
import com.furkanerd.tickets.repository.UserRepository;
import com.furkanerd.tickets.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}
