package com.codingarpan.filestoragemanager.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "imageinfos")
@Data
public class ImageInfo {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String email;

        @Column(unique = true)
        private String imagename;
}
