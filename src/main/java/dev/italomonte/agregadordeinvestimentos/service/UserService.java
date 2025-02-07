package dev.italomonte.agregadordeinvestimentos.service;

import dev.italomonte.agregadordeinvestimentos.controller.CreateUserDto;
import dev.italomonte.agregadordeinvestimentos.controller.UpdateUserDto;
import dev.italomonte.agregadordeinvestimentos.entity.User;
import dev.italomonte.agregadordeinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {

        //DTO -> Entity
        var entity = new User(
                null,
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var userSaved = userRepository.save(entity);
        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findById(UUID.fromString(userId));
    }

}
