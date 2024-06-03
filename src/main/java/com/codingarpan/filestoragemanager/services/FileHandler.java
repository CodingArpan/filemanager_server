package com.codingarpan.filestoragemanager.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileHandler {
    String uploadFile(MultipartFile file, String email);
    void deleteFile(String fileName);
    List<String> allFiles(String email);
    String presignedUrl(String fileName);

}
