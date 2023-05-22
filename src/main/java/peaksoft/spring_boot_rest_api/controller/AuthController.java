package peaksoft.spring_boot_rest_api.controller;

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
import peaksoft.spring_boot_rest_api.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jwt")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    private final LoginMapper loginMapper;

    private final AuthenticationManager authenticationManager;

    @PostMapping("signup")
    public UserResponse signUp(@RequestBody UserRequest userRequest) {
        System.out.println("I'm controller");
        return userService.create(userRequest);
    }

    @PostMapping("sign-in")
    public LoginResponse signIn(@RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        authenticationManager.authenticate(token);

        User user = userRepository.findByUsername(token.getName()).get();
        return loginMapper.loginView(jwtTokenUtil.generateToken(user), "successful", user);
    }


}
