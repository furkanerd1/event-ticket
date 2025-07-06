package com.furkanerd.tickets.controller;

import com.furkanerd.tickets.mapper.TicketMapper;
import com.furkanerd.tickets.model.dto.internal.GetTicketResponseDto;
import com.furkanerd.tickets.model.dto.internal.ListTicketResponseDto;
import com.furkanerd.tickets.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.furkanerd.tickets.util.JwtUtil.parseUuid;

@RestController
@RequestMapping(path = "/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public ResponseEntity<Page<ListTicketResponseDto>> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable){
        return ResponseEntity.ok(ticketService.listTicketsForUser(parseUuid(jwt),pageable)
                .map(ticketMapper::listTicketResponseDto));
    }

    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketResponseDto> getTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("ticketId") UUID ticketId){
        return ticketService.getTicket(parseUuid(jwt),ticketId)
                .map(ticketMapper::toGetTicketResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
