package ru.practicum.emw.main.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.user.dto.NewUserRequest;
import ru.practicum.emw.main.user.entity.User;

import java.util.List;

public interface UserAdminService {

    User save(NewUserRequest newUserRequest);

    List<User> findByIds(List<Long> ids, Pageable pageable);

    void deleteById(long id);

}
