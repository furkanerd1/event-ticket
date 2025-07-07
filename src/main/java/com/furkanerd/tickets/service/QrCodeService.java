package com.furkanerd.tickets.service;


import com.furkanerd.tickets.model.entity.QrCode;
import com.furkanerd.tickets.model.entity.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
