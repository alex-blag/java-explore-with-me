package ru.practicum.emw.main.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.category.entity.Category;
import ru.practicum.emw.main.category.service.CategoryPublicService;
import ru.practicum.emw.main.category.service.CategoryService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryPublicServiceImpl implements CategoryPublicService {

    private final CategoryService categoryService;

    @Override
    public Category findById(long id) {
        log.debug("findById (id = {})", id);

        return categoryService.findById(id);
    }

    @Override
    public List<Category> findAll(Pageable pageable) {
        log.debug("findAll (pageable = {})", pageable);

        return categoryService.findAll(pageable);
    }

}
