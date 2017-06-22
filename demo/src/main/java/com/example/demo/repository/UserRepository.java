package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	List<User> findAllByLoginNameContainingOrderByIdDesc(String loginName);
	
	Page<User> findAll(Pageable pageable);
}
