package ru.practicum.emw.main.user.service;

import ru.practicum.emw.main.user.entity.User;

public interface UserPrivateService {

    User findById(long id);

    boolean existsById(long id);

}
