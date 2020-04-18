package com.task.group.ignitis.controller;

import com.task.group.ignitis.entity.User;
import com.task.group.ignitis.security.SecurityValidator;
import com.task.group.ignitis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private SecurityValidator validator;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validator.validatePayload(bindingResult));
        }

        User userFromDB = userService.findByUsername(user.getUsername());

        if (userFromDB == null) {
            String encodedPassword = validator.getEncodedPassword(user.getPassword());

            user.setPassword(encodedPassword);
            userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body("User is registered successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + "'" + user.getUsername() + "' already exist.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validator.validatePayload(bindingResult));
        }

        User userFromDB = userService.findByUsername(user.getUsername());

        if (userFromDB != null && validator.checkPasswords(user.getPassword(), userFromDB.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body("User with username " + "'" + user.getUsername() + "' is logged in.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is invalid!");
    }
}
