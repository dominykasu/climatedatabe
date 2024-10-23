package lt.ca.javau10.climatedata.services;

import lt.ca.javau10.climatedata.config.JwtUtils;
import lt.ca.javau10.climatedata.entities.ERole;
import lt.ca.javau10.climatedata.entities.Role;
import lt.ca.javau10.climatedata.entities.User;
import lt.ca.javau10.climatedata.entities.UserDto;
import lt.ca.javau10.climatedata.payload.requests.LoginRequest;
import lt.ca.javau10.climatedata.payload.requests.SignupRequest;
import lt.ca.javau10.climatedata.payload.responses.JwtResponse;
import lt.ca.javau10.climatedata.payload.responses.MessageResponse;
import lt.ca.javau10.climatedata.repositories.RoleRepository;
import lt.ca.javau10.climatedata.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    AuthService authService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAuthenticateUser_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("dominykas");
        loginRequest.setPassword("password");

        // Create a User and Role for testing
        User user = new User("dominykas", "email@one.lt", "password");
        Role userRole = new Role();
        userRole.setName(ERole.ROLE_USER);

        // Create UserDto and set the fields accordingly
        UserDto userDto = new UserDto();
        userDto.setId(1L);  // Set the ID
        userDto.setUsername("dominykas");  // Set the username
        userDto.setEmail("email@one.lt");  // Set the email
        userDto.setAuthorities(Set.of(userRole));  // Set the role

        // Mock authentication and JWT generation
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(any())).thenReturn("jwt-token");
        when(authentication.getPrincipal()).thenReturn(userDto);

        // Act
        JwtResponse response = authService.authenticateUser(loginRequest);

        // Assert
        assertEquals("jwt-token", response.getToken());
        assertEquals("dominykas", response.getUsername());  // Assert that the username matches
        assertEquals("email@one.lt", response.getEmail());  // Assert that the email matches
        assertTrue(response.getRoles().contains("ROLE_USER"));  // Check that the role is correct
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("dominykas");
        signupRequest.setEmail("email@one.lt");
        signupRequest.setPassword("password");
        signupRequest.setRole(Set.of("USER"));

        User user = new User("dominykas", "email@one.lt", "encoded-password");
        Role userRole = new Role();
        userRole.setId(1L); // Assign a role ID
        userRole.setName(ERole.ROLE_USER);

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class))).thenReturn(user); // Mock save method

        // Act
        MessageResponse response = authService.registerUser(signupRequest);

        // Assert
        assertEquals("User registered successfully!", response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUser_UserExists() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("dominykas");
        signupRequest.setEmail("email@one.lt");
        signupRequest.setPassword("password");

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.registerUser(signupRequest);
        });

        assertEquals("Error: Username is already taken!", exception.getReason());
    }

    @Test
    void testRegisterUser_EmailExists() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("dominykas");
        signupRequest.setEmail("email@one.lt");
        signupRequest.setPassword("password");

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.registerUser(signupRequest);
        });

        assertEquals("Error: Email is already in use!", exception.getReason());
    }
}