package com.randstad.events.controllers;

import com.randstad.events.domain.models.Event;
import com.randstad.events.domain.models.User;
import com.randstad.events.domain.services.UserService;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(path = "api/events/v1")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(this.userService.findAll());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/userByEmail/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/users")
    public ResponseEntity<User> create(@RequestBody User user){
        return ResponseEntity.ok(this.userService.create(user));
    }
    @PutMapping("/users/{id}")
    public void update(@PathVariable Long id, @RequestBody User user ){
        userService.update(id, user);
    }
    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable Long id){
        this.userService.delete(id);
    }
    @PutMapping("/myEvent/{userId}/{eventId}")
    public ResponseEntity<User> enrollUserToEvent(@PathVariable Long userId, @PathVariable Long eventId){
        return ResponseEntity.ok(this.userService.enrollUserToEvent(userId, eventId));
    }
    @GetMapping("/users/{userId}/events")
    public Set<Event> getUserEvents(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return user.getEvents();
    }

    @DeleteMapping("/removeMyEvent/{userId}/{eventId}")
    public ResponseEntity<User> unenrollUserFromEvent(@PathVariable Long userId, @PathVariable Long eventId){
        try {
            User user = this.userService.unenrollUserFromEvent(userId, eventId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
