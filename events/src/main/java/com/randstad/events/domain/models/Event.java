package com.randstad.events.domain.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    @Column(length = 1000)

    private String description;

    private String image;

    public Event() {
    }

    public Event(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Event(Long id, String title, String description, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
