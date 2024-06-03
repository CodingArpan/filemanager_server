package com.codingarpan.filestoragemanager.services.Implement;

import com.codingarpan.filestoragemanager.dtos.SignupReqDTO;
import com.codingarpan.filestoragemanager.dtos.UserDTO;
import com.codingarpan.filestoragemanager.models.User;
import com.codingarpan.filestoragemanager.repos.UserRepo;
import com.codingarpan.filestoragemanager.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDTO createUser(SignupReqDTO signupDTO) {
        User user = new User();
//        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
        User createdUser = userRepo.save(user);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(createdUser.getId());
        userDTO.setEmail(createdUser.getEmail());
//        userDTO.setName(createdUser.getName());

        return userDTO;
    }

    @Override
    public UserDTO verifyUser(String email, String password) {
        User user = userRepo.findFirstByEmail(email);
        if (user == null) {
            return null;
        }
        if (new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
//            userDTO.setName(user.getName());
            return userDTO;
        }
        return null;
    }


}
