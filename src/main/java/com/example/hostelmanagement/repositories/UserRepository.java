package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> getAllByUserNameContains(String userName);

    User getUserByUserNameAndPasswordAndUserStatus(String userName, String password, boolean userStatus);

    User findByUserNameAndAndPassword(String username, String password);

    List<User> findAllByUserNameContains(String username);
    List<User> findAllByUserStatusTrue();
    User findOneByUserId(int userid);
}
