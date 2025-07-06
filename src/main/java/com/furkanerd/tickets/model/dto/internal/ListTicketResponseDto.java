package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.enums.TicketStatusEnum;

import java.util.UUID;

public record ListTicketResponseDto(
        UUID id,
        TicketStatusEnum status,
        ListTicketTypeResponseDto ticketType
){}
