package com.furkanerd.tickets.service.impl;

import com.furkanerd.tickets.exception.TicketTypeNotFoundException;
import com.furkanerd.tickets.exception.TicketsSoldOutException;
import com.furkanerd.tickets.exception.UserNotFoundException;
import com.furkanerd.tickets.model.entity.Ticket;
import com.furkanerd.tickets.model.entity.TicketType;
import com.furkanerd.tickets.model.entity.User;
import com.furkanerd.tickets.model.enums.TicketStatusEnum;
import com.furkanerd.tickets.repository.TicketRepository;
import com.furkanerd.tickets.repository.TicketTypeRepository;
import com.furkanerd.tickets.repository.UserRepository;
import com.furkanerd.tickets.service.QrCodeService;
import com.furkanerd.tickets.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        // Find the user who wants to buy ticket
        User user = userRepository.findById(userId).
                orElseThrow(() ->  new UserNotFoundException("User not found with ID "+userId));

        // Find the ticket type which user wants to buy
        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId)
                .orElseThrow(() -> new TicketTypeNotFoundException("Ticket Type not found with ID "+ticketTypeId));

        // check the tickets are sold out or not
        int purchasedTicket = ticketRepository.countByTicketTypeId(ticketType.getId());
        int totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTicket + 1 >  totalAvailable) {
            throw new TicketsSoldOutException();
        }

        // generate ticket
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);
        ticket.setStatus(TicketStatusEnum.PURCHASED);

        // save in repo
        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);


    }
}
