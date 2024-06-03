package com.codingarpan.filestoragemanager.dtos;

import lombok.Data;
import lombok.Setter;
@Data
public class UserDTO {
    @Setter
    private Long id;

//    private String name;

    private String email;

    private String password;


}
