package ru.practicum.emw.main.user.service.impl;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.emw.main.user.entity.User;
import ru.practicum.emw.main.user.repository.UserRepository;
import ru.practicum.emw.main.user.service.UserService;

import java.util.List;

import static ru.practicum.emw.main.common.CheckUtils.checkUserExistsOrThrow;
import static ru.practicum.emw.main.exception.ExceptionUtils.getUserNotFoundException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        log.debug("save (user = {})", user);

        return userRepository.save(user);
    }

    @Override
    public User findById(long id) {
        log.debug("findById (id = {})", id);

        return userRepository
                .findById(id)
                .orElseThrow(() -> getUserNotFoundException(id));
    }

    @Override
    public List<User> findAll(Predicate predicate, Pageable pageable) {
        log.debug("findAll (predicate = [{}], pageable = {})", predicate, pageable);

        return userRepository
                .findAll(predicate, pageable)
                .getContent();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        log.debug("deleteById (id = {})", id);

        checkUserExistsOrThrow(id, this.existsById(id));
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(long id) {
        log.debug("existsById (id = {})", id);

        return userRepository.existsById(id);
    }

}
