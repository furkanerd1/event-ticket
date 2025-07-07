package com.furkanerd.tickets.service.impl;

import com.furkanerd.tickets.exception.QrCodeGenerationException;
import com.furkanerd.tickets.exception.QrCodeNotFoundException;
import com.furkanerd.tickets.model.entity.QrCode;
import com.furkanerd.tickets.model.entity.Ticket;
import com.furkanerd.tickets.model.enums.QrCodeStatusEnum;
import com.furkanerd.tickets.repository.QrCodeRepository;
import com.furkanerd.tickets.service.QrCodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QrCodeRepository qrCodeRepository;
    private final QRCodeWriter qrCodeWriter;

    @Override
    public QrCode generateQrCode(Ticket ticket) {
        QrCode qrCode = new QrCode();
       try{
           UUID uniqueId = UUID.randomUUID();
           String qrCodeImage= generateQrCodeImage(uniqueId);

           qrCode.setId(uniqueId);
           qrCode.setStatus(QrCodeStatusEnum.ACTIVE);
           qrCode.setValue(qrCodeImage);
           qrCode.setTicket(ticket);

           return qrCodeRepository.saveAndFlush(qrCode);

       }catch (IOException | WriterException ex){
            throw new QrCodeGenerationException("Failed to generate QR_CODE ",ex);
       }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId,userId)
                .orElseThrow(QrCodeNotFoundException::new);

        try{
            return Base64.getDecoder().decode(qrCode.getValue());
        }catch (IllegalArgumentException ex){
            log.error("Invalid base64 QR Code for ticket id ",ticketId ,ex);
            throw new QrCodeNotFoundException();
        }

    }

    private String generateQrCodeImage(UUID uniqueId) throws WriterException, IOException {
        // Qr Code format to --> BitMatrix(2D)
        BitMatrix bitMatrix = qrCodeWriter.encode(
                uniqueId.toString(),
                BarcodeFormat.QR_CODE,
                QR_WIDTH,
                QR_HEIGHT
        );

        // BitMatrix to --> Image
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            ImageIO.write(image,"PNG",b);
            byte[] imageBytes = b.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
