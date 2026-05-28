package org.seniorcare.identityaccess.application.queries.handlers.user;

import org.seniorcare.identityaccess.application.dto.user.UserDTO;
import org.seniorcare.identityaccess.application.queries.impl.user.FindAllUsersQuery;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class FindAllUsersQueryHandler {

    private final IUserRepository userRepository;

    public FindAllUsersQueryHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> handle(FindAllUsersQuery query) {
        Pagination pagination = new Pagination(
                query.pageable().getPageNumber(),
                query.pageable().getPageSize()
        );
        PageResult<UserDTO> result = userRepository.findAll(pagination).map(user -> new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail().value(),
                user.getPhone(),
                user.isActive(),
                user.getRoleName(),
                user.getPhotoUrl(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        ));
        return new PageImpl<>(
                result.content(),
                PageRequest.of(result.currentPage(), result.pageSize()),
                result.totalElements()
        );
    }
}
