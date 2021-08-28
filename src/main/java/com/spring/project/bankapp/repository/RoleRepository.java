package com.spring.project.bankapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.project.bankapp.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}
