package com.furkanerd.tickets.service;

import com.furkanerd.tickets.model.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    Page<Ticket> listTicketsForUser(UUID userId , Pageable pageable);

    Optional<Ticket> getTicket(UUID userId , UUID ticketId);
}
