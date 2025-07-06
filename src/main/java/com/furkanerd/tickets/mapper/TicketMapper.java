package com.furkanerd.tickets.mapper;

import com.furkanerd.tickets.model.dto.internal.GetTicketResponseDto;
import com.furkanerd.tickets.model.dto.internal.ListTicketResponseDto;
import com.furkanerd.tickets.model.dto.internal.ListTicketTypeResponseDto;
import com.furkanerd.tickets.model.entity.Ticket;
import com.furkanerd.tickets.model.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketResponseDto listTicketResponseDto(Ticket ticket);

    ListTicketTypeResponseDto listTicketTypeResponseDto(TicketType ticketType);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
    @Mapping(target = "eventStartDate", source = "ticket.ticketType.event.start")
    @Mapping(target = "eventEndDate", source = "ticket.ticketType.event.end")
    GetTicketResponseDto toGetTicketResponseDto(Ticket ticket);

}
