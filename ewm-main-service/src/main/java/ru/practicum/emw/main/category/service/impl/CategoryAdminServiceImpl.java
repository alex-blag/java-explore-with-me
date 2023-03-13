package ru.practicum.emw.main.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.category.dto.CategoryDto;
import ru.practicum.emw.main.category.dto.NewCategoryDto;
import ru.practicum.emw.main.category.entity.Category;
import ru.practicum.emw.main.category.service.CategoryAdminService;
import ru.practicum.emw.main.category.service.CategoryService;
import ru.practicum.emw.main.event.service.EventAdminService;

import static org.springframework.util.StringUtils.hasText;
import static ru.practicum.emw.main.common.CheckUtils.checkCategoryHasNoAssociatedEventsOrThrow;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryService categoryService;

    @Lazy
    private final EventAdminService eventAdminService;

    @Override
    @Transactional
    public Category save(NewCategoryDto newCategoryDto) {
        log.debug("save (newCategoryDto = {})", newCategoryDto);

        Category category = new Category();

        category.setName(newCategoryDto.getName());

        return categoryService.save(category);
    }

    @Override
    @Transactional
    public Category updateById(long id, CategoryDto categoryDto) {
        log.debug("updateById (id = {}, categoryDto = {})", id, categoryDto);

        Category category = this.findById(id);

        String name = categoryDto.getName();
        if (hasText(name)) {
            category.setName(name);
        }

        return category;
    }

    @Override
    public Category findById(long id) {
        log.debug("findById (id = {})", id);

        return categoryService.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        log.debug("deleteById (id = {})", id);

        checkCategoryHasNoAssociatedEventsOrThrow(id, eventAdminService.existsByCategoryId(id));
        categoryService.deleteById(id);
    }

}
