package com.furkanerd.tickets.model.dto.internal;

import java.util.UUID;

public record GetPublishedEventTicketTypesResponseDto(
        UUID id,
        String name,
        Double price,
        String description
){}
