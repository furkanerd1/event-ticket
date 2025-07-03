package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.enums.EventStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateEventResponseDto(
        UUID id,
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue,
        LocalDateTime salesStartDate,
        LocalDateTime salesEndDate,
        EventStatusEnum status,
        List<UpdateTicketTypeResponseDto> ticketTypes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
