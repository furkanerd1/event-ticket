package com.furkanerd.tickets.model.dto.internal;

import java.util.UUID;

public record TicketTypeResponseDto(
        UUID id,
        String name,
        Integer totalAvailable,
        Double price,
        String description
){}
