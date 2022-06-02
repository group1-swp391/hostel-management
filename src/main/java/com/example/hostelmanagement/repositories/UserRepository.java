package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM tbl_Users AS u") List<User> getAll();

    User findByUserNameAndAndPassword(String username, String password);
    User findUserByUserId(int userid);





}
