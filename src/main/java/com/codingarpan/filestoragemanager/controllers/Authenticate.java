package com.codingarpan.filestoragemanager.controllers;

import com.codingarpan.filestoragemanager.dtos.AuthenticationReqDTO;
import com.codingarpan.filestoragemanager.dtos.AuthenticationResDTO;
import com.codingarpan.filestoragemanager.dtos.JwtResponseDTO;
import com.codingarpan.filestoragemanager.services.AuthService;
import com.codingarpan.filestoragemanager.services.Implement.UserDetailsImpl;
import com.codingarpan.filestoragemanager.utils.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class Authenticate {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsImpl userDetailsImpl;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*",allowCredentials = "true", methods = {RequestMethod.POST})
    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationReqDTO authenticationReqDTO,HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationReqDTO.getEmail(), authenticationReqDTO.getPassword()));
        } catch (BadCredentialsException e) {
           return  ResponseEntity.badRequest().body("Incorrect username or password!");
        } catch (DisabledException disabledException) {
            return ResponseEntity.badRequest().body("Server side error");
        }
//        System.out.println(authentication.isAuthenticated());

        if(authentication.isAuthenticated()){
            UserDetails userDetails = userDetailsImpl.loadUserByUsername(authenticationReqDTO.getEmail());
            String accessToken = jwtUtils.generateToken(userDetails.getUsername());
            // set accessToken to cookie header
            ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                    .httpOnly(true) // Make the cookie HTTP only
                    .secure(true) // Ensure the cookie is sent over HTTPS
                    .path("/") // Set the path for the cookie
                    .maxAge(7 * 24 * 60 * 60) // Set the max age to 7 days
                    .sameSite("Strict") // Set the SameSite attribute
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.status(200).body(new AuthenticationResDTO(userDetails.getUsername()));

        } else {
            return ResponseEntity.badRequest().body("Incorrect username or password!");
        }


    }

    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*",allowCredentials = "true", methods = {RequestMethod.POST})
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true) // Make the cookie HTTP only
                .secure(true) // Ensure the cookie is sent over HTTPS
                .path("/") // Set the path for the cookie
                .maxAge(0) // Set the max age to 7 days
                .sameSite("Strict") // Set the SameSite attribute
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.status(200).body("Logged out successfully");
    }
}
