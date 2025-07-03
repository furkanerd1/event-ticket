package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.dto.request.CreateTicketTypeRequest;
import com.furkanerd.tickets.model.enums.EventStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record UpdateEventRequestDto(

        @NotNull(message = " Id is not be null")
        UUID id,

        @NotBlank(message = "Event type name is required")
        String name,

        LocalDateTime start,
        LocalDateTime end,
        @NotBlank(message = "Venue information is required")
        String venue,
        LocalDateTime salesStartDate,
        LocalDateTime salesEndDate,
        @NotNull(message = "Event status must be provided")
        EventStatusEnum status,

        @NotEmpty(message = "At least one ticket type required")
        List<UpdateTicketTypeRequestDto> ticketTypes
){
    public UpdateEventRequestDto {
        if(ticketTypes == null) ticketTypes = new ArrayList<>();
    }
}
