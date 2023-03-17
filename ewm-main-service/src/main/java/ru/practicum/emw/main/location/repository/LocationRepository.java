package ru.practicum.emw.main.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.emw.main.location.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long>, QuerydslPredicateExecutor<Location> {
}
