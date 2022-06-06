package com.example.hostelmanagement.repositories;

import com.example.hostelmanagement.models.Contracts;
import com.example.hostelmanagement.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {






}
