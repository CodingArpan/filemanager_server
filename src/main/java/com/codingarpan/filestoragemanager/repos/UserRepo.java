package com.codingarpan.filestoragemanager.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codingarpan.filestoragemanager.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);
}
