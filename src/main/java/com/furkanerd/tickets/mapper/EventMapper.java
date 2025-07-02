package com.furkanerd.tickets.mapper;

import com.furkanerd.tickets.model.dto.internal.*;
import com.furkanerd.tickets.model.dto.request.CreateEventRequest;
import com.furkanerd.tickets.model.dto.request.CreateTicketTypeRequest;
import com.furkanerd.tickets.model.entity.Event;
import com.furkanerd.tickets.model.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateEventRequest fromDto(CreateEventRequestDto createEventRequestDto);

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequest createTicketTypeRequestDto);

    CreateEventResponseDto toResponseDto(Event event);

    ListEventResponseDto toListResponseDto(Event event);

    TicketTypeResponseDto toTicketTypeResponseDto(TicketType ticketType);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    GetEventTicketTypeResponseDto toGetEventTicketTypeResponseDto(TicketType ticketType);
}
