package org.seniorcare.identityaccess.application.queries.handlers;

import org.seniorcare.identityaccess.application.dto.UserDTO;
import org.seniorcare.identityaccess.application.queries.impl.FindUserByIdQuery;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FindUserByIdQueryHandler {

    private final IUserRepository userRepository;

    public FindUserByIdQueryHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public Optional<UserDTO> handle(FindUserByIdQuery query) {
        return userRepository.findById(query.id())
                .map(this::toDTO);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.isActive()
        );
    }
}
