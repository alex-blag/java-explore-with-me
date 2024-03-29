package ru.practicum.emw.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.category.dto.CategoryDto;
import ru.practicum.emw.main.category.entity.Category;
import ru.practicum.emw.main.category.service.CategoryPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.emw.main.category.dto.CategoryMapper.toCategoryDto;
import static ru.practicum.emw.main.category.dto.CategoryMapper.toCategoryDtos;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_FROM;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_SIZE;
import static ru.practicum.emw.main.common.CommonUtils.getPageRequestUnsorted;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
@Validated
class CategoryPublicController {

    private final CategoryPublicService categoryPublicService;

    @GetMapping
    List<CategoryDto> getAll(
            @RequestParam(defaultValue = DEFAULT_FROM) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_SIZE) @Positive int size
    ) {
        log.debug("getAll (from = {}, size = {})", from, size);

        Pageable pageable = getPageRequestUnsorted(from, size);
        List<Category> categories = categoryPublicService.findAll(pageable);

        return toCategoryDtos(categories);
    }

    @GetMapping("/{catId}")
    CategoryDto get(
            @PathVariable long catId
    ) {
        log.debug("get (catId = {})", catId);

        Category category = categoryPublicService.findById(catId);

        return toCategoryDto(category);
    }

}
