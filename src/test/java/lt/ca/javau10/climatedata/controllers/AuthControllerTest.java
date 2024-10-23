package lt.ca.javau10.climatedata.controllers;

import lt.ca.javau10.climatedata.payload.requests.LoginRequest;
import lt.ca.javau10.climatedata.payload.requests.SignupRequest;
import lt.ca.javau10.climatedata.payload.responses.JwtResponse;
import lt.ca.javau10.climatedata.payload.responses.MessageResponse;
import lt.ca.javau10.climatedata.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateUser_shouldReturnJwtResponse_whenLoginIsValid() {
        LoginRequest loginRequest = new LoginRequest("testUser", "password");
        JwtResponse jwtResponse = new JwtResponse("token", 1L, "testUser", "testEmail", null);

        when(authService.authenticateUser(loginRequest)).thenReturn(jwtResponse);

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        assertEquals(ResponseEntity.ok(jwtResponse), response);
    }

    @Test
    void registerUser_shouldReturnMessageResponse_whenSignupIsValid() {
        SignupRequest signupRequest = new SignupRequest("testUser", "testEmail", "password");
        MessageResponse messageResponse = new MessageResponse("User registered successfully!");

        when(authService.registerUser(signupRequest)).thenReturn(messageResponse);

        ResponseEntity<?> response = authController.registerUser(signupRequest);

        assertEquals(ResponseEntity.ok(messageResponse), response);
    }

    @Test
    void registerUser_shouldReturnErrorResponse_whenSignupFails() {
        SignupRequest signupRequest = new SignupRequest("testUser", "testEmail", "password");

        ResponseStatusException exception = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");

        when(authService.registerUser(signupRequest)).thenThrow(exception);

        ResponseEntity<?> response = authController.registerUser(signupRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username already exists", ((MessageResponse)response.getBody()).getMessage());
    }
}
