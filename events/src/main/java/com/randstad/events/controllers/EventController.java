package com.randstad.events.controllers;

import com.randstad.events.domain.models.Event;
import com.randstad.events.domain.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAll(){
        return ResponseEntity.ok(this.eventService.findAll());
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<Event> getById(@PathVariable Long id) {
        return ResponseEntity.ok(this.eventService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Event> create(@RequestParam("title") String title,
                                          @RequestParam("description") String description,
                                          @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(this.eventService.create(title, description, image));
    }
}
