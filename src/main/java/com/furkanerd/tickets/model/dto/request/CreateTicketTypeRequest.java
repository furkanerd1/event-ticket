package com.furkanerd.tickets.model.dto.request;

import com.furkanerd.tickets.model.entity.Event;

public record CreateTicketTypeRequest(
        String name,
        Integer totalAvailable,
        Double price,
        String description
){}
