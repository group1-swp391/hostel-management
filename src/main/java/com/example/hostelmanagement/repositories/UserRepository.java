package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM tbl_Users AS u Where u.userName LIKE %:userName%")
    public List<User> getAllByAName(@Param("userName") String userName);

    @Query(nativeQuery = true, value = "SELECT * FROM tbl_Users AS u Where u.userName = :userName AND u.password = :password AND u.userStatus = 1")
    public User getUserByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
}
