package com.furkanerd.tickets.controller;

import com.furkanerd.tickets.mapper.EventMapper;
import com.furkanerd.tickets.model.dto.internal.*;
import com.furkanerd.tickets.model.dto.request.CreateEventRequest;
import com.furkanerd.tickets.model.entity.Event;
import com.furkanerd.tickets.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.furkanerd.tickets.util.JwtUtil.parseUuid;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(@AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto) {
        CreateEventRequest request = eventMapper.fromDto(createEventRequestDto);
        UUID userId = UUID.fromString(jwt.getSubject());
        Event createdEvent = eventService.createEvent(userId,request);
        return new ResponseEntity<>(eventMapper.toResponseDto(createdEvent), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        UUID userId = parseUuid(jwt);
        Page<Event> events  = eventService.listEventsForOrganizer(userId,pageable);
        return  ResponseEntity.ok(events.map(eventMapper::toListResponseDto));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEventDetails(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventId) {
        UUID userId = parseUuid(jwt);
        return eventService.getEventForOrganizer(eventId,userId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent
            (@AuthenticationPrincipal Jwt jwt,
             @Valid @RequestBody UpdateEventRequestDto requestDto,
             @PathVariable UUID eventId){
        UUID userId = parseUuid(jwt);
        Event updatedEvent = eventService.updateEvent(eventId,userId,eventMapper.fromUpdateEventRequestDto(requestDto));
        return ResponseEntity.ok(eventMapper.toUpdateEventResponseDto(updatedEvent));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@AuthenticationPrincipal Jwt jwt, @PathVariable UUID eventId) {
        UUID userId = parseUuid(jwt);
        eventService.deleteEvent(eventId, userId);
        return ResponseEntity.noContent().build();
    }

}
