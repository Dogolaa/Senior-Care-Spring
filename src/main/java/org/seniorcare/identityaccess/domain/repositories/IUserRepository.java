package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.User;
import java.util.Optional;
import java.util.UUID;


public interface IUserRepository {


    void save(User user);


    Optional<User> findById(UUID id);


    Optional<User> findByEmail(String email);


    void deleteById(UUID id);
}
