package ru.practicum.emw.main.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.user.entity.User;
import ru.practicum.emw.main.user.service.UserPrivateService;
import ru.practicum.emw.main.user.service.UserService;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserPrivateServiceImpl implements UserPrivateService {

    private final UserService userService;

    @Override
    public User findById(long id) {
        log.debug("findById (id = {})", id);

        return userService.findById(id);
    }

    @Override
    public boolean existsById(long id) {
        log.debug("existsById (id = {})", id);

        return userService.existsById(id);
    }

}
