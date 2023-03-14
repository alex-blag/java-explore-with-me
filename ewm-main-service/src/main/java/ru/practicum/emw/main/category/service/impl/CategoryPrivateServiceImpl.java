package ru.practicum.emw.main.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.category.entity.Category;
import ru.practicum.emw.main.category.service.CategoryPrivateService;
import ru.practicum.emw.main.category.service.CategoryService;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryPrivateServiceImpl implements CategoryPrivateService {

    private final CategoryService categoryService;

    @Override
    public Category findById(long id) {
        log.debug("findById (id = {})", id);

        return categoryService.findById(id);
    }

}
