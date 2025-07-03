package com.furkanerd.tickets.model.dto.internal;


import java.time.LocalDateTime;
import java.util.UUID;

public record ListPublishedEventResponseDto(
        UUID id,
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue
){}
