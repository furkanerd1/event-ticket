package com.furkanerd.tickets.model.dto.internal;

import java.util.UUID;

public record ListTicketTypeResponseDto(
        UUID id,
        String name,
        Double price
){}
