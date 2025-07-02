package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.enums.EventStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record GetEventDetailsResponseDto(
         UUID id,
         String name,
         LocalDateTime start,
         LocalDateTime end,
         String venue,
         LocalDateTime salesStartDate,
         LocalDateTime salesEndDate,
         EventStatusEnum status,
         List<GetEventTicketTypeResponseDto> ticketTypes,
         LocalDateTime createdAt,
         LocalDateTime updatedAt
) {
    public GetEventDetailsResponseDto{
        if (ticketTypes == null) ticketTypes = new ArrayList<>();
    }
}


