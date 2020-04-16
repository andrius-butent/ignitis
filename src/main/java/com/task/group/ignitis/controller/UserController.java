package com.task.group.ignitis.controller;

import com.task.group.ignitis.entity.User;
import com.task.group.ignitis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorMessage(bindingResult));
        }

        Integer id = userService.registerUser(user);

        if (id != null && id > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User is registered successfully!");
        } else if (id != null && id == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + "'" + user.getUsername() + "' already exist.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorMessage(bindingResult));
        }

        if (userService.loginUser(user)) {
            return ResponseEntity.status(HttpStatus.OK).body("User " + "'" + user.getUsername() + "' is logged in.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is invalid!");
        }
    }

    private String getErrorMessage(BindingResult bindingResult) {

        String errorMessage = "";

        if (bindingResult.hasErrors()) {

            // collecting all errors
            for(ObjectError error : bindingResult.getAllErrors()) {
                errorMessage += error.getDefaultMessage() + "\n";
            }
        }

        return errorMessage;
    }
}
