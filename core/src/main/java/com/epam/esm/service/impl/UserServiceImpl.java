package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers(Map<String, String> params) {
        List<UserDto> userDTOS = new ArrayList<>();
        int limit = Integer.parseInt(params.get("size"));
        int offset = (Integer.parseInt(params.get("page")) - 1) * limit;
        userRepository.getUsers(limit, offset).forEach(user -> userDTOS.add(userMapper.toDTO(user)));
        return userDTOS;
    }

    @Override
    public long getCount() {
        return userRepository.getCount();
    }

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.getUserById(id);
        return userMapper.toDTO(user);
    }


}
