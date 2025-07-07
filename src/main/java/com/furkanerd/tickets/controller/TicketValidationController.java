package com.furkanerd.tickets.controller;

import com.furkanerd.tickets.mapper.TicketValidationMapper;
import com.furkanerd.tickets.model.dto.internal.TicketValidationRequestDto;
import com.furkanerd.tickets.model.dto.internal.TicketValidationResponseDto;
import com.furkanerd.tickets.model.entity.TicketValidation;
import com.furkanerd.tickets.model.enums.TicketValidationMethod;
import com.furkanerd.tickets.service.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
    ){
        TicketValidation ticketValidation;
        TicketValidationMethod ticketValidationMethod = ticketValidationRequestDto.method();
        if(TicketValidationMethod.MANUAL.equals(ticketValidationMethod)){
            ticketValidation  = ticketValidationService.validateTicketByTicketId(ticketValidationRequestDto.id());
        }else{
            ticketValidation = ticketValidationService.validateTicketByQrCode(ticketValidationRequestDto.id());
        }
        return ResponseEntity.ok(ticketValidationMapper.toTicketValidationResponseDto(ticketValidation));
    }
}
