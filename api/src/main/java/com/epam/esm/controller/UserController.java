package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam Map<String, String> params) {
        List<UserDto> users = userService.getUsers(params);
        long usersCount = userService.getCount();
        return users;
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") long id) {
        UserDto userDTO = userService.getUserById(id);
        return userDTO;
    }
}
