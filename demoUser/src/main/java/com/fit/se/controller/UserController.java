package com.fit.se.controller;

import com.fit.se.dto.ResponseDto;
import com.fit.se.entity.User;
import com.fit.se.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        return new ResponseEntity(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable("id") int userId){
        ResponseDto responseDto = userService.getUserById(userId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userService.getAllUser();
        return ResponseEntity.ok(userList);
    }

}
