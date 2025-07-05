package com.furkanerd.tickets.repository;

import com.furkanerd.tickets.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Integer countByTicketTypeId(UUID ticketTypeId);
}
