package com.furkanerd.tickets.service.impl;

import com.furkanerd.tickets.exception.QrCodeNotFoundException;
import com.furkanerd.tickets.exception.TicketNotFoundException;
import com.furkanerd.tickets.model.entity.QrCode;
import com.furkanerd.tickets.model.entity.Ticket;
import com.furkanerd.tickets.model.entity.TicketValidation;
import com.furkanerd.tickets.model.enums.QrCodeStatusEnum;
import com.furkanerd.tickets.model.enums.TicketValidationMethod;
import com.furkanerd.tickets.model.enums.TicketValidationStatusEnum;
import com.furkanerd.tickets.repository.QrCodeRepository;
import com.furkanerd.tickets.repository.TicketRepository;
import com.furkanerd.tickets.repository.TicketValidationRepository;
import com.furkanerd.tickets.service.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketRepository ticketRepository;
    private final TicketValidationRepository ticketValidationRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException("Qr code not found with Id "+ " "+ qrCodeId));

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket);
    }

    @Override
    public TicketValidation validateTicketByTicketId(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with Id "+ " "+ ticketId));

        return validateTicket(ticket);
    }

    private TicketValidation validateTicket(Ticket ticket){
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(TicketValidationMethod.QR_SCAN);

        // If ticket has already been validated (VALID), it cannot be used again -> mark as INVALID
        TicketValidationStatusEnum ticketValidationStatusEnum = ticket.getValidations().stream()
                .filter(validation -> TicketValidationStatusEnum.VALID.equals(validation.getStatus()))
                .findFirst()
                .map(validation -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatusEnum);
        return ticketValidationRepository.save(ticketValidation);
    }
}
