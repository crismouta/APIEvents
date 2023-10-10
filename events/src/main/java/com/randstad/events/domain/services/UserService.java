package com.randstad.events.domain.services;

import com.randstad.events.domain.models.Event;
import com.randstad.events.domain.models.User;
import com.randstad.events.infra.repositories.IEventRepository;
import com.randstad.events.infra.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository userRepository;

    @Autowired
    private IEventRepository eventRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public User create (User user) {
        return this.userRepository.save(user);
    }

    public void delete (Long id) {

        this.userRepository.deleteById(id);
    }

    public void update(Long id, User user) {

        User existingUser = findById(id);
        if (existingUser == null ) {
            System.out.println("error");
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setProfession(user.getProfession());

        userRepository.save(existingUser);
    }

    public User enrollUserToEvent(Long userId, Long eventId) {
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.findById(eventId).get();
        user.enrollEvent(event);
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User unenrollUserFromEvent(Long userId, Long eventId) {
        // Buscar el usuario y el evento en la base de datos
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        // Verificar si el usuario y el evento existen
        if (userOptional.isPresent() && eventOptional.isPresent()) {
            User user = userOptional.get();
            Event event = eventOptional.get();

            // Desvincular al usuario del evento (eliminar la relación en la tabla intermedia)
            user.removeEvent(event);

            // Guardar los cambios en la base de datos
            userRepository.save(user);

            return user;
        } else {
            // Devolver null si el usuario o el evento no existen
            return null;
        }
    }
}
