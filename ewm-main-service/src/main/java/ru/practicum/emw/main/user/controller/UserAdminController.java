package ru.practicum.emw.main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.emw.main.user.dto.NewUserRequest;
import ru.practicum.emw.main.user.dto.UserDto;
import ru.practicum.emw.main.user.entity.User;
import ru.practicum.emw.main.user.service.UserAdminService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_FROM;
import static ru.practicum.emw.main.common.CommonUtils.DEFAULT_SIZE;
import static ru.practicum.emw.main.common.CommonUtils.getPageRequestUnsorted;
import static ru.practicum.emw.main.user.dto.UserMapper.toUserDto;
import static ru.practicum.emw.main.user.dto.UserMapper.toUsersDto;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@RequiredArgsConstructor
@Validated
class UserAdminController {

    private final UserAdminService userAdminService;

    @GetMapping
    List<UserDto> getAll(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = DEFAULT_FROM) @PositiveOrZero int from,
            @RequestParam(defaultValue = DEFAULT_SIZE) @Positive int size
    ) {
        log.debug("getAll (ids = {}, from = {}, size = {})", ids, from, size);

        Pageable pageable = getPageRequestUnsorted(from, size);
        List<User> users = userAdminService.findByIds(ids, pageable);
        return toUsersDto(users);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto post(
            @RequestBody @Valid NewUserRequest newUserRequest
    ) {
        log.debug("post (newUserRequest = {})", newUserRequest);

        User user = userAdminService.save(newUserRequest);
        return toUserDto(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable long userId
    ) {
        log.debug("delete (userId = {})", userId);

        userAdminService.deleteById(userId);
    }

}
