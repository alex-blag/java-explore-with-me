package ru.practicum.ewm.stats.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.stats.server.entity.ServiceApp;

public interface ServiceAppRepository extends JpaRepository<ServiceApp, Long> {

    ServiceApp findByName(String name);

}
