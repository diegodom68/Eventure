package com.eventure.services;

import com.eventure.models.Event;
import com.eventure.models.User;
import com.eventure.repositories.EventRepository;
import com.eventure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public Event update(Long id, Event updated) {
        Event original = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        original.setTitle(updated.getTitle());
        original.setDescription(updated.getDescription());
        original.setDate(updated.getDate());
        original.setLocation(updated.getLocation());
        return eventRepository.save(original);
    }
    public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
}
}
