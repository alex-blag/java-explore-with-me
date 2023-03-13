package ru.practicum.emw.main.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.emw.main.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
