package com.codingarpan.filestoragemanager.controllers;

import com.codingarpan.filestoragemanager.services.FileHandler;
import com.codingarpan.filestoragemanager.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class AppController {

    private FileHandler uploadService;
    @Autowired
    private JWTUtils jwtUtils;

    public AppController(FileHandler uploadService) {
        this.uploadService = uploadService;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET})
    @RequestMapping(value="/hello",method = RequestMethod.GET)
    public String hello() {
        return "Hello World";
    }



    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*",allowCredentials = "true", methods = {RequestMethod.POST})
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,@CookieValue(value = "accessToken", required = false) String myCookie) {
        System.out.println(myCookie);
        try{
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("Please select a file!");
                }


        if (myCookie == null || jwtUtils.isTokenExpired(myCookie)){
            return ResponseEntity.badRequest().body("Cookie is broken or expired!");
        }
        String email = jwtUtils.extractUsername(myCookie);
        String fileName = this.uploadService.uploadFile(file,email);
        return ResponseEntity.ok(fileName);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error in uploading file");
        }
    }




    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*",allowCredentials = "true", methods = {RequestMethod.POST})
    @RequestMapping(value = "/allfiles",method = RequestMethod.POST)
    public ResponseEntity<?> allFiles(@CookieValue(value = "accessToken", required = false) String myCookie) {
        System.out.println(myCookie);
        try {

        if (myCookie == null || jwtUtils.isTokenExpired(myCookie)){
            return ResponseEntity.badRequest().body("Cookie is broken or expired!");
        }
        String email = jwtUtils.extractUsername(myCookie);
        return ResponseEntity.ok(this.uploadService.allFiles(email));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error in fetching files");
        }
    }





}
