package com.codingarpan.filestoragemanager.repos;

import com.codingarpan.filestoragemanager.models.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageInfoRepo extends JpaRepository<ImageInfo, Long> {
    ImageInfo[] findAllByEmail(String email);
}
