package dev.italomonte.agregadordeinvestimentos.service;

import dev.italomonte.agregadordeinvestimentos.controller.CreateUserDto;
import dev.italomonte.agregadordeinvestimentos.controller.UpdateUserDto;
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
import static org.mockito.Mockito.*;

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

    @Nested
    class listUsers {

        @Test
        @DisplayName("Should return all users with success")
        void shouldReturnAllUsersWithSuccess() {

            // Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(userList)
                    .when(userRepository)
                    .findAll();

            // Act
            var output = userService.listUsers();

            // Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteUserById {

        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExist() {

            // Arrange
            // Mockando chamadas existById e deleeteById
            doReturn(true).when(userRepository).existsById(uuidUserArgumentCaptor.capture());
            doNothing().when(userRepository).deleteById(uuidUserArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            // Act
            userService.deleteUserById(userId.toString());

            // Assert
            var idList = uuidUserArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should delete user with success when user not exists")
        void shouldDeleteUserWithSuccessWhenUserNotExist() {

            // Arrange
            // Mockando chamadas existById
            doReturn(false)
                    .when(userRepository)
                    .existsById(uuidUserArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            // Act
            userService.deleteUserById(userId.toString());

            // Assert
            assertEquals(userId, uuidUserArgumentCaptor.getValue());

            verify(userRepository, times(1))
                    .existsById( uuidUserArgumentCaptor.getValue());

            // Cenário onde que que o metodo deleteById n seja chamado, por isso o any
            verify(userRepository, times(0)).deleteById(any());
        }
    }

            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidUserArgumentCaptor.capture());

            // Act
            var output = userService.getUserById(userId.toString());

            // Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidUserArgumentCaptor.getValue());
        }
    }
}