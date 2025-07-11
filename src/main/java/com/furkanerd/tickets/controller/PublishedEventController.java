package com.furkanerd.tickets.controller;

import com.furkanerd.tickets.mapper.EventMapper;
import com.furkanerd.tickets.model.dto.internal.GetPublishedEventDetailsResponseDto;
import com.furkanerd.tickets.model.dto.internal.ListPublishedEventResponseDto;
import com.furkanerd.tickets.model.entity.Event;
import com.furkanerd.tickets.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String query,
            Pageable pageable) {

        Page<Event> events = (query != null && !query.trim().isEmpty())
                ? eventService.searchPublishedEvents(query, pageable)
                : eventService.listPublishedEvents(pageable);

        return ResponseEntity.ok(events.map(eventMapper::toListPublishedEventResponseDto));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEvent(@PathVariable UUID eventId) {

        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }
}
