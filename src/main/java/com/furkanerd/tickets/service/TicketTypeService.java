package com.furkanerd.tickets.service;

import com.furkanerd.tickets.model.entity.Ticket;

import java.util.UUID;

public interface TicketTypeService {

    Ticket purchaseTicket(UUID userId,UUID ticketTypeId);
}
