package com.furkanerd.tickets.repository;

import com.furkanerd.tickets.model.entity.Event;
import com.furkanerd.tickets.model.enums.EventStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);

    Optional<Event> findByIdAndOrganizerId(UUID eventId, UUID organizerId);

    Page<Event> findByStatus(EventStatusEnum status, Pageable pageable);

    @Query(value = """
        SELECT * FROM events
        WHERE status = 'PUBLISHED'
        AND to_tsvector('english', CONCAT_WS(' ', name, venue))
         @@ plainto_tsquery('english', :searchTerm)
        """,
        countQuery = """ 
            SELECT COUNT(*) FROM events
            WHERE status = 'PUBLISHED'
            AND to_tsvector('english', CONCAT_WS(' ', name, venue))
            @@ plainto_tsquery('english', :searchTerm)
        """,
        nativeQuery = true
    )
    Page<Event> searchPublishedEvents(@Param("searchTerm") String searchTerm, Pageable pageable);

    Optional<Event> findByIdAndStatus(UUID eventId, EventStatusEnum status);
}
