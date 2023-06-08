package peaksoft.spring_boot_rest_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peaksoft.spring_boot_rest_api.dto.*;
import peaksoft.spring_boot_rest_api.entity.User;
import peaksoft.spring_boot_rest_api.repository.UserRepository;
import peaksoft.spring_boot_rest_api.security.jwt.JwtTokenUtil;
import peaksoft.spring_boot_rest_api.service.StudentService;
import peaksoft.spring_boot_rest_api.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
@Tag(name = "Auth Api")
public class AuthController {
    private final StudentService studentService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private  final UserService userService;

    private final LoginMapper loginMapper;

    private final AuthenticationManager authenticationManager;

    @PostMapping("signup")
    @Operation(summary = "Sing up",description = "User can register")
    public UserResponse signUp(@RequestBody UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @PostMapping("sign-in")
    @Operation(summary = "sign on",description ="User can sign in")
    public LoginResponse signIn(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        authenticationManager.authenticate(token);

        User user = userRepository.findByEmail(token.getName()).get();
        return loginMapper.loginView(jwtTokenUtil.generateToken(user), "successful", user);
    }


}
