package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.enums.TicketValidationMethod;

import java.util.UUID;

public record TicketValidationRequestDto(
    UUID id,
    TicketValidationMethod method
){}
