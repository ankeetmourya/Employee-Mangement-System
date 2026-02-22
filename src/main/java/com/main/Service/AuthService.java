package com.main.Service;

import com.main.DTO.LoginRequest;
import com.main.DTO.RegisterRequest;
import com.main.Entity.User;
import com.main.Repository.UserRepository;
import com.main.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {

        if (repo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (repo.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

//        if (!request.getPassword().equals(request.getConfirmPassword())) {
//            throw new RuntimeException("Passwords do not match");
//        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        repo.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest request) {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(user);
    }


}