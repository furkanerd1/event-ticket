package com.furkanerd.tickets.model.dto.internal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record UpdateTicketTypeRequestDto(

        @NotNull(message = " Id is not be null")
        UUID id,

        @NotBlank(message = "Ticket type name is required")
        String name,

        Integer totalAvailable,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or greater")
        Double price,

        String description
){}
