package org.seniorcare.identityaccess.application.queries.handlers.user;

import org.seniorcare.identityaccess.application.dto.user.UserDTO;
import org.seniorcare.identityaccess.application.queries.impl.user.FindAllUsersQuery;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindAllUsersQueryHandler {

    private final IUserRepository userRepository;

    public FindAllUsersQueryHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> handle(FindAllUsersQuery query) {
        Page<User> userPage = userRepository.findAll(query.pageable());
        return userPage.map(this::toDTO);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}