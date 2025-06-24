package com.furkanerd.tickets.model.dto.request;

import com.furkanerd.tickets.model.enums.EventStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record CreateEventRequest(
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue,
        LocalDateTime salesStartDate,
        LocalDateTime salesEndDate,
        EventStatusEnum status,
        List<CreateTicketTypeRequest> ticketTypes
){
    public CreateEventRequest {
        if(ticketTypes == null) ticketTypes = new ArrayList<>();
    }
}
