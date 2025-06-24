package com.furkanerd.tickets.model.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateTicketTypeRequestDto(

        @NotBlank(message = "Ticket type name is required")
        String name,

        Integer totalAvailable,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or greater")
        Double price,

        String description
){}
