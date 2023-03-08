package com.eminetekcan.BlogApp.service.impl;

import com.eminetekcan.BlogApp.payload.UserDto;
import com.eminetekcan.BlogApp.entity.User;
import com.eminetekcan.BlogApp.exception.ResourceNotFoundException;
import com.eminetekcan.BlogApp.repository.UserRepository;
import com.eminetekcan.BlogApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        User user=modelMapper.map(userDto,User.class);
        return user;
    }

    private UserDto userToDto(User user){
        UserDto userDto=modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
