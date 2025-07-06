package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.enums.TicketStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetTicketResponseDto(
        UUID id,
        TicketStatusEnum status,
        Double price,
        String description,
        String eventName,
        String eventVenue,
        LocalDateTime eventStartDate,
        LocalDateTime eventEndDate
){}
