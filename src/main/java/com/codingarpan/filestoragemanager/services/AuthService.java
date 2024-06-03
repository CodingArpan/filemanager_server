package com.codingarpan.filestoragemanager.services;

import com.codingarpan.filestoragemanager.dtos.SignupReqDTO;
import com.codingarpan.filestoragemanager.dtos.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupReqDTO signupReqDTO);
    UserDTO verifyUser(String email, String password);
}
