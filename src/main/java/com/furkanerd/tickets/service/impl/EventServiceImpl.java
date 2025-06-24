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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest createEventRequest) {
        User organizer = userRepository.findById(organizerId).orElseThrow(() -> new UserNotFoundException("User not found with ID "+organizerId));

        List<TicketType> ticketTypeList = createEventRequest.ticketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.name());
                    ticketTypeToCreate.setPrice(ticketType.price());
                    ticketTypeToCreate.setDescription(ticketType.description());
                    ticketTypeToCreate.setTotalAvailable(ticketType.totalAvailable());
                    return ticketTypeToCreate;
                }
        ).toList();

        Event toCreate = Event.builder()
                .name(createEventRequest.name())
                .start(createEventRequest.start())
                .end(createEventRequest.end())
                .venue(createEventRequest.venue())
                .salesStartDate(createEventRequest.salesStartDate())
                .salesEndDate(createEventRequest.salesEndDate())
                .status(createEventRequest.status())
                .organizer(organizer)
                .ticketTypes(ticketTypeList)
                .build();

        return eventRepository.save(toCreate);
    }
}
