package ru.practicum.emw.main.user.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.user.dto.NewUserRequest;
import ru.practicum.emw.main.user.entity.QUser;
import ru.practicum.emw.main.user.entity.User;
import ru.practicum.emw.main.user.service.UserAdminService;
import ru.practicum.emw.main.user.service.UserService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {

    private static final QUser Q_USER = QUser.user;

    private final UserService userService;

    @Override
    @Transactional
    public User save(NewUserRequest newUserRequest) {
        log.debug("save (newUserRequest = {})", newUserRequest);

        User user = new User();

        user.setName(newUserRequest.getName());

        user.setEmail(newUserRequest.getEmail());

        return userService.save(user);
    }

    @Override
    public List<User> findByIds(List<Long> ids, Pageable pageable) {
        log.debug("findByIds (ids = {}, pageable = {})", ids, pageable);

        Predicate p = buildQUserPredicateByIds(ids);
        return userService.findAll(p, pageable);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        log.debug("deleteById (id = {})", id);

        userService.deleteById(id);
    }

    private Predicate buildQUserPredicateByIds(List<Long> ids) {
        BooleanBuilder builder = new BooleanBuilder();

        if (ids != null) {
            builder.and(Q_USER.id.in(ids));
        }

        return builder;
    }

}
