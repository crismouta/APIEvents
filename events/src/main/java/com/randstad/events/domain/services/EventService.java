package com.randstad.events.domain.services;

import com.randstad.events.domain.models.Event;
import com.randstad.events.infra.repositories.IEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final IEventRepository eventRepository;

    private final CloudinaryImageServiceImplementation cloudinaryImageService;

    public EventService(IEventRepository eventRepository, CloudinaryImageServiceImplementation cloudinaryImageService) {
        this.eventRepository = eventRepository;
        this.cloudinaryImageService = cloudinaryImageService;
    }

    public List<Event> findAll() {
        return this.eventRepository.findAll();
    }

    public Event findById(Long id) {
        Optional<Event> optionalProject = this.eventRepository.findById(id);
        return optionalProject.orElse(null);
    }

    public Event create(String title, String description, MultipartFile image) throws IOException {
        String imageUrl = (String) cloudinaryImageService.upload(image).get("url");

        Event project = new Event(title, description, imageUrl );
        return eventRepository.save(project);

    }
}
