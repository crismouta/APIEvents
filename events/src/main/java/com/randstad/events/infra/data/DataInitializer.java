
package com.randstad.events.infra.data;

import com.randstad.events.domain.models.Event;
import com.randstad.events.infra.repositories.IEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private IEventRepository eventRepository;
    @Override
    public void run(String... args) throws Exception {
        // Crear instancias de eventos y guardarlos en la base de datos
        Event event1 = new Event();
        event1.setTitle("Event 1 new");
        event1.setImage("https://t.ctcdn.com.br/mh0foo99fyjt6M7kgmsEr67RslQ=/1024x576/smart/i595923.jpeg");
        event1.setDescription("Description 1");

        Event event2 = new Event();
        event2.setTitle("Event 2");
        event2.setImage("https://t.ctcdn.com.br/mh0foo99fyjt6M7kgmsEr67RslQ=/1024x576/smart/i595923.jpeg");
        event2.setDescription("Description 2");

        Event event3 = new Event();
        event3.setTitle("Event 3");
        event3.setImage("https://t.ctcdn.com.br/mh0foo99fyjt6M7kgmsEr67RslQ=/1024x576/smart/i595923.jpeg");
        event3.setDescription("Description 3");



        // Guardar los eventos en la base de datos
        eventRepository.save(event1);
        eventRepository.save(event2);
        eventRepository.save(event3);
    }
}
