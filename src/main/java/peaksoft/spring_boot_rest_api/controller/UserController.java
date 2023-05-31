package peaksoft.spring_boot_rest_api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.spring_boot_rest_api.dto.UserRequest;
import peaksoft.spring_boot_rest_api.dto.UserResponse;
import peaksoft.spring_boot_rest_api.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    public UserResponse getById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("addAdmin")
    public UserResponse createAdmin(@RequestBody UserRequest request) {
        return userService.create(request);

    }
    @PostMapping("addInstructor")
    public UserResponse createInstructor(@RequestBody UserRequest request) {
        return userService.create(request);
    }

    @PutMapping("{id}")
    public UserResponse update(@PathVariable("id") Long userId, @RequestBody UserRequest request) {
        return userService.update(userId, request);

    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long userId) {
        return userService.delete(userId);
    }
}
