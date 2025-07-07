package com.furkanerd.tickets.service;

import com.furkanerd.tickets.model.entity.TicketValidation;

import java.util.UUID;

public interface TicketValidationService {

    TicketValidation validateTicketByQrCode(UUID qrCodeId);

    TicketValidation validateTicketByTicketId(UUID ticketId);
}
