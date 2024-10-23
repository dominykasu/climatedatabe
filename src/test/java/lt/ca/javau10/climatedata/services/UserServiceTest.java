package lt.ca.javau10.climatedata.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import lt.ca.javau10.climatedata.entities.ERole;
import lt.ca.javau10.climatedata.entities.Role;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.UserDto;
import lt.ca.javau10.climatedata.repositories.RoleRepository;
import lt.ca.javau10.climatedata.repositories.UserRepository;
import lt.ca.javau10.climatedata.utils.EntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        // Arrange
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserByUsername_shouldReturnUser_whenUserExists() {
        // Arrange
        User mockUser = new User();
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUserByUsername("testUser");

        // Assert
        assertNotNull(result);
        verify(userRepository).findByUsername("testUser");
    }

    @Test
    void getUserByUsername_shouldReturnNull_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        // Act
        User result = userService.getUserByUsername("nonExistingUser");

        // Assert
        assertNull(result);
        verify(userRepository).findByUsername("nonExistingUser");
    }

    @Test
    void getUserById_shouldReturnUser_whenUserExists() {
        // Arrange
        User mockUser = new User();
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_shouldReturnNull_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        User result = userService.getUserById(999L);

        // Assert
        assertNull(result);
        verify(userRepository).findById(999L);
    }

    @Test
    void createUser_shouldSaveUser() {
        // Arrange
        User mockUser = new User();
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        // Act
        User result = userService.createUser(mockUser);

        // Assert
        assertNotNull(result);
        verify(userRepository).save(mockUser);
    }

    @Test
    void updateUser_shouldReturnNull_whenUserDoesNotExist() {
        // Arrange
        User updatedUser = new User();
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        User result = userService.updateUser(999L, updatedUser);

        // Assert
        assertNull(result);
        verify(userRepository).findById(999L);
    }

    @Test
    void deleteUser_shouldDeleteUserById() {
        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).deleteById(1L);
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        UserDto mockUserDto = new UserDto();  // Your custom UserDto class
        when(entityMapper.toUserDto(mockUser)).thenReturn(mockUserDto);

        // Act
        UserDetails result = userService.loadUserByUsername("testUser");

        // Assert
        assertNotNull(result);
        verify(userRepository).findByUsername("testUser");
        verify(entityMapper).toUserDto(mockUser);
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("nonExistingUser");
        });

        assertEquals("User Not Found with username: nonExistingUser", thrown.getMessage());
        verify(userRepository).findByUsername("nonExistingUser");
    }
}

