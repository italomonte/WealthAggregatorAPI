package dev.italomonte.agregadordeinvestimentos.service;

import dev.italomonte.agregadordeinvestimentos.controller.CreateUserDto;
import dev.italomonte.agregadordeinvestimentos.entity.User;
import dev.italomonte.agregadordeinvestimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks // cria uma instacia da classe de service, injectando os mocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor // Captura o argumento que estamos passando dentro de um método
    private ArgumentCaptor<UUID> uuidUserArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {

            // Arrange
            var user = new User(
              UUID.randomUUID(),
              "username",
              "email@email.com",
              "password",
              Instant.now(),
              null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            var input = new CreateUserDto(
                    "username",
                    "username@email.com",
                    "123"
                    );

            // Act
            var output = userService.createUser(input);

            // Assert
            assertNotNull(output);
            var userCapture = userArgumentCaptor.getValue();

            // verify if the user passed to method createUser is the same passed to the .save()
            assertEquals(input.username(), userCapture.getUsername());
            assertEquals(input.email(), userCapture.getEmail());
            assertEquals(input.password(), userCapture.getPassword());
        }

        @Test
        @DisplayName("Should Throw exception when error occurs")
        void shoudThrowExcepetionWhenErronOccurs(){
            // Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "username@email.com",
                    "123"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));

        }

    }

    @Nested
    class getUserById {
        @Test
        @DisplayName("Get User By Id With Success When Optional Is Present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {

            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user))
                    .when(userRepository)
                    .findById(uuidUserArgumentCaptor.capture());

            // Act
            var output = userService.getUserById(user.getUserId().toString());

            // Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidUserArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Get User By Id With Success When Optional Is Empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIEmpty() {

            // Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidUserArgumentCaptor.capture());

            // Act
            var output = userService.getUserById(userId.toString());

            // Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidUserArgumentCaptor.getValue());
        }
    }

