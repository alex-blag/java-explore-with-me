package ru.practicum.emw.main.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.category.entity.Category;
import ru.practicum.emw.main.category.repository.CategoryRepository;
import ru.practicum.emw.main.category.service.CategoryService;

import java.util.List;

import static ru.practicum.emw.main.common.CheckUtils.checkCategoryExistsOrThrow;
import static ru.practicum.emw.main.exception.ExceptionUtils.getCategoryNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category save(Category category) {
        log.debug("save (category = {})", category);

        return categoryRepository.save(category);
    }

    @Override
    public Category findById(long id) {
        log.debug("findById (id = {})", id);

        return categoryRepository
                .findById(id)
                .orElseThrow(() -> getCategoryNotFoundException(id));
    }

    @Override
    public List<Category> findAll(Pageable pageable) {
        log.debug("findAll (pageable = {})", pageable);

        return categoryRepository
                .findAll(pageable)
                .getContent();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        log.debug("deleteById (id = {})", id);

        checkCategoryExistsOrThrow(id, categoryRepository.existsById(id));
        categoryRepository.deleteById(id);
    }

}
