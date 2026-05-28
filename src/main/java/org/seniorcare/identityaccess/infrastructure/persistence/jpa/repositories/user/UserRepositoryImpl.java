package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.user;

import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.UserMapper;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class UserRepositoryImpl implements IUserRepository {

    private final SpringDataUserRepository jpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(SpringDataUserRepository jpaRepository, UserMapper userMapper) {
        this.jpaRepository = jpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        jpaRepository.save(userMapper.toModel(user));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(userMapper::toEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(userMapper::toEntity);
    }

    @Override
    public PageResult<User> findAll(Pagination pagination) {
        Page<User> page = jpaRepository
                .findAllOrderedByActiveFirst(PageRequest.of(pagination.page(), pagination.size()))
                .map(userMapper::toEntity);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pagination.page(), pagination.size());
    }
}
