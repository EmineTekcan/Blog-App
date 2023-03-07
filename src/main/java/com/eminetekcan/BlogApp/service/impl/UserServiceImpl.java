package com.eminetekcan.BlogApp.service.impl;

import com.eminetekcan.BlogApp.payload.UserDto;
import com.eminetekcan.BlogApp.entity.User;
import com.eminetekcan.BlogApp.exception.ResourceNotFoundException;
import com.eminetekcan.BlogApp.repository.UserRepository;
import com.eminetekcan.BlogApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=dtoToUser(userDto);
        User savedUser=userRepository.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=userRepository.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepository.findAll();
        return users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer id) {
       User user=userRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
       userRepository.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        return User.builder()
                .name(userDto.getName())
                .about(userDto.getAbout())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    private UserDto userToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .about(user.getAbout())
                .password(user.getPassword())
                .build();
    }
}
