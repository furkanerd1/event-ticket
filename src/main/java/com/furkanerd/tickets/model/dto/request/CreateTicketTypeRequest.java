package com.furkanerd.tickets.model.dto.request;


public record CreateTicketTypeRequest(
        String name,
        Integer totalAvailable,
        Double price,
        String description
){}
