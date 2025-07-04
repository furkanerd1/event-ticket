package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.entity.TicketType;
import com.furkanerd.tickets.model.enums.EventStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record GetPublishedEventDetailsResponseDto(
        UUID id,
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue,
        List<GetPublishedEventTicketTypesResponseDto> ticketTypes
){
    public GetPublishedEventDetailsResponseDto{
        if(ticketTypes == null) ticketTypes = new ArrayList<>();
    }
}
