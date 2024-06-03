package com.codingarpan.filestoragemanager.controllers;

import com.codingarpan.filestoragemanager.dtos.SignupReqDTO;
import com.codingarpan.filestoragemanager.dtos.UserDTO;
import com.codingarpan.filestoragemanager.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class Signup {

    @Autowired
    private AuthService authService;
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupReqDTO signupDTO) {
        System.out.println(signupDTO);
        UserDTO createdUser = authService.createUser(signupDTO);
        if (createdUser == null){
            return new ResponseEntity<>("User not created, come again later!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
