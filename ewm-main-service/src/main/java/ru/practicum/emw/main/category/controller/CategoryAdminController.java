package ru.practicum.emw.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.category.dto.CategoryDto;
import ru.practicum.emw.main.category.dto.NewCategoryDto;
import ru.practicum.emw.main.category.entity.Category;
import ru.practicum.emw.main.category.service.CategoryAdminService;

import javax.validation.Valid;

import static ru.practicum.emw.main.category.dto.CategoryMapper.toCategoryDto;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto post(
            @RequestBody @Valid NewCategoryDto newCategoryDto
    ) {
        log.debug("post (newCategoryDto = {})", newCategoryDto);

        Category category = categoryAdminService.save(newCategoryDto);

        return toCategoryDto(category);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable long catId
    ) {
        log.debug("delete (catId = {})", catId);

        categoryAdminService.deleteById(catId);
    }

    @PatchMapping("/{catId}")
    CategoryDto patch(
            @PathVariable long catId,
            @RequestBody @Valid CategoryDto categoryDto
    ) {
        log.debug("patch (catId = {}, categoryDto = {})", catId, categoryDto);

        Category category = categoryAdminService.updateById(catId, categoryDto);

        return toCategoryDto(category);
    }

}
