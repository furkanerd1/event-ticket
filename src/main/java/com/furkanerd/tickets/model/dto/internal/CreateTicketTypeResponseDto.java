package com.furkanerd.tickets.model.dto.internal;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTicketTypeResponseDto(
        UUID id,
        String name,
        Integer totalAvailable,
        Double price,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
