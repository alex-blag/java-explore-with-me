package ru.practicum.emw.main.user.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import ru.practicum.emw.main.user.entity.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findById(long userId);

    List<User> findAll(Predicate predicate, Pageable pageable);

    void deleteById(long id);

    boolean existsById(long id);

}
