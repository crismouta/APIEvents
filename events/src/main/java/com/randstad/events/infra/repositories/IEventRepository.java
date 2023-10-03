package com.randstad.events.infra.repositories;

import com.randstad.events.domain.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event, Long> {
}
