package com.codingarpan.filestoragemanager.services.Implement;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.codingarpan.filestoragemanager.dtos.ImageInfoDTO;
import com.codingarpan.filestoragemanager.models.ImageInfo;
import com.codingarpan.filestoragemanager.repos.ImageInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.codingarpan.filestoragemanager.services.FileHandler;
@Service
public class FileHandlerImpl implements FileHandler {
    @Autowired
    private AmazonS3 client;
    @Value("${app.s3.bucketName}")
    private String bucketName;
    @Override
    public String uploadFile(MultipartFile file, String email) {
        if(file.isEmpty()) {
            return "File is empty";
        }

        String fileName = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String newfileName = uuid.toString() + fileName.substring(fileName.lastIndexOf("."));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        try {
            client.putObject(bucketName, newfileName, file.getInputStream(), objectMetadata);
            ImageInfoDTO imageInfoDTO = new ImageInfoDTO();
            imageInfoDTO.setEmail(email);
            imageInfoDTO.setImagename(newfileName);
            return this.saveImageInfo(imageInfoDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error in uploading file, please try again";
        }

    }

    @Autowired
    private ImageInfoRepo imageInfoRepo;
     public String saveImageInfo(ImageInfoDTO imageInfoDTO) {
         ImageInfo imageInfo = new ImageInfo();
         imageInfo.setEmail(imageInfoDTO.getEmail());
         imageInfo.setImagename(imageInfoDTO.getImagename());
         ImageInfo createdImageInfo = imageInfoRepo.save(imageInfo);
         return this.presignedUrl(createdImageInfo.getImagename());

     }


    @Override
    public void deleteFile(String fileName) {

    }

    @Override
    public List<String> allFiles(String email) {
         ImageInfo imageInfo[] = imageInfoRepo.findAllByEmail(email);
            List<String> urlList = List.of(imageInfo).stream().map(x -> this.presignedUrl(x.getImagename())).toList();
        return urlList;
    }

    @Override
    public String presignedUrl(String fileName) {
        Date expirationDate = new Date();
        long expTimeMillis = expirationDate.getTime();
        Integer hours=1;
        expTimeMillis += hours * 1000 * 60 * 60;
        expirationDate.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(com.amazonaws.HttpMethod.GET)
                .withExpiration(expirationDate);
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}
