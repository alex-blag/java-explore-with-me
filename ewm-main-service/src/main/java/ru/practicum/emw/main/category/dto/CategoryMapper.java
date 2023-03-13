package ru.practicum.emw.main.category.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.emw.main.category.entity.Category;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();

        dto.setId(category.getId());
        dto.setName(category.getName());

        return dto;
    }

    public static List<CategoryDto> toCategoryDtos(List<Category> categories) {
        return categories
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(toList());
    }

}
