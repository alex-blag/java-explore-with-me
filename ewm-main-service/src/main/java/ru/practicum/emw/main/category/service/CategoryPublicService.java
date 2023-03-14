package ru.practicum.emw.main.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.category.entity.Category;

import java.util.List;

public interface CategoryPublicService {

    Category findById(long id);

    List<Category> findAll(Pageable pageable);

}
