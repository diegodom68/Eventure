package com.eventure.controllers;

import com.eventure.models.Event;
import com.eventure.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<Event> getAll() {
        return eventService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<?> create(@RequestBody Event event, Principal principal) {
        event.setCreatedAt(LocalDateTime.now());
        // Obtener el usuario autenticado
        String email = principal.getName();
        var user = eventService.getUserByEmail(email);

        // Validar si tiene rol adecuado (por seguridad extra)
        boolean isOrganizer = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ORGANIZER") || role.getName().equals("ROLE_ADMIN"));

        if (!isOrganizer) {
            return ResponseEntity.status(403).body("No tienes permisos para crear eventos");
        }

        event.setOrganizer(user);
        return ResponseEntity.ok(eventService.create(event));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ORGANIZER', 'ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Event event) {
        return ResponseEntity.ok(eventService.update(id, event));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }
}
