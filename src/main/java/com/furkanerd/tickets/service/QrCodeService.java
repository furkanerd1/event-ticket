package com.furkanerd.tickets.service;


import com.furkanerd.tickets.model.entity.QrCode;
import com.furkanerd.tickets.model.entity.Ticket;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
}
