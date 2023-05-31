package peaksoft.spring_boot_rest_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.spring_boot_rest_api.dto.UserRequest;
import peaksoft.spring_boot_rest_api.dto.UserResponse;
import peaksoft.spring_boot_rest_api.entity.Role;
import peaksoft.spring_boot_rest_api.entity.User;
import peaksoft.spring_boot_rest_api.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest request) {
        System.out.println("I'm user service");
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getRoleName() == null) {
            user.setRole(Role.STUDENT);
        } else {
            user.setRole(Role.valueOf(request.getRoleName()));
        }
        user.setLocalDate(LocalDate.now());
        repository.save(user);
        System.out.println("I'm done in user service");
        return mapToResponse(user);
    }

    public UserResponse mapToResponse(User user) {
        System.out.println("I'm in response method");
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roleName(user.getRole().name())
                .localDate(user.getLocalDate()).build();
    }

    public List<UserResponse> getAll() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : repository.findAll()) {
            userResponses.add(mapToResponse(user));
        }
        return userResponses;
    }

    public UserResponse getUserById(Long id) {
        User user = repository.findById(id).get();
        return mapToResponse(user);
    }

    public UserResponse update(Long userId, UserRequest request) {
        User user = repository.findById(userId).get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRoleName()));
        repository.save(user);
        return mapToResponse(user);
    }

    public String delete(Long userId) {
        repository.delete(repository.findById(userId).get());
//        repository.deleteById(userId);
        return "Successfully deleted user with id: " + userId;
    }
}
