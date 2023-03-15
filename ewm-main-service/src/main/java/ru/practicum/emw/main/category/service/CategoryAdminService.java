package ru.practicum.emw.main.category.service;

import ru.practicum.emw.main.category.dto.CategoryDto;
import ru.practicum.emw.main.category.dto.NewCategoryDto;
import ru.practicum.emw.main.category.entity.Category;

public interface CategoryAdminService {

    Category save(NewCategoryDto newCategoryDto);

    Category updateById(long id, CategoryDto categoryDto);

    Category findById(long id);

    void deleteById(long id);

}
