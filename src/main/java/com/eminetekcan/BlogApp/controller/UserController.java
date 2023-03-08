package com.eminetekcan.BlogApp.controller;

import com.eminetekcan.BlogApp.payload.ApiResponse;
import com.eminetekcan.BlogApp.payload.UserDto;
import com.eminetekcan.BlogApp.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id){
        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") Integer userId){
        return new ResponseEntity<>(userService.updateUser(userDto,userId),HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse("User successfully deleted",true),HttpStatus.OK);
    }

}
