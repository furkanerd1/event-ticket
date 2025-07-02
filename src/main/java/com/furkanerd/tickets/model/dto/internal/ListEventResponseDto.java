package com.furkanerd.tickets.model.dto.internal;

import com.furkanerd.tickets.model.enums.EventStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ListEventResponseDto(
        UUID id,
        String name,
        LocalDateTime start,
        LocalDateTime end,
        String venue,
        LocalDateTime salesStartDate,
        LocalDateTime salesEndDate,
        EventStatusEnum status,
        List<TicketTypeResponseDto> ticketTypes
){
    public ListEventResponseDto {
        if(ticketTypes == null) ticketTypes = new ArrayList<>();
    }
}
