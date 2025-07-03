package com.furkanerd.tickets.model.dto.request;

import java.util.UUID;

public record UpdateTicketTypeRequest(
        UUID id,
        String name,
        Integer totalAvailable,
        Double price,
        String description
){}
