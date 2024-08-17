package acikgoz.kaan.UserSecurityAPI.controller;

import acikgoz.kaan.UserSecurityAPI.dto.request.LoginRequest;
import acikgoz.kaan.UserSecurityAPI.dto.request.RegisterRequest;
import acikgoz.kaan.UserSecurityAPI.dto.response.LoginResponse;
import acikgoz.kaan.UserSecurityAPI.dto.response.RegisterResponse;
import acikgoz.kaan.UserSecurityAPI.message.ResponseMessage;
import acikgoz.kaan.UserSecurityAPI.security.jwt.JwtUtils;
import acikgoz.kaan.UserSecurityAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserJwtController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserJwtController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.saveUser(registerRequest);

        RegisterResponse registerResponse = new RegisterResponse(
                ResponseMessage.REGISTER_RESPONSE_MESSAGE,
                true
        );
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken upAt = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(upAt);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateJwtToken(userDetails);
        LoginResponse loginResponse = new LoginResponse(token);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
