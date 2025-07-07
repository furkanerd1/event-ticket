package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.enums.TicketValidationStatusEnum;

import java.util.UUID;

public record TicketValidationResponseDto(
    UUID ticketId,
    TicketValidationStatusEnum status
){}
