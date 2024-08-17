package acikgoz.kaan.UserSecurityAPI.service;

import acikgoz.kaan.UserSecurityAPI.dto.request.RegisterRequest;
import acikgoz.kaan.UserSecurityAPI.entity.Role;
import acikgoz.kaan.UserSecurityAPI.entity.Role.RoleType;
import acikgoz.kaan.UserSecurityAPI.entity.User;
import acikgoz.kaan.UserSecurityAPI.exception.ConflictException;
import acikgoz.kaan.UserSecurityAPI.message.ErrorMessage;
import acikgoz.kaan.UserSecurityAPI.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException(
                        String.format(ErrorMessage.USER_NOT_FOUND_EXCEPTION, email)
                )
        );
    }

    public void saveUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE ,registerRequest.getEmail()));
        }

        Role role = roleService.findOrCreateRole(RoleType.EMPLOYEE);
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User user = new User(
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                encodedPassword,
                roles
        );
        userRepository.save(user);
    }

}
