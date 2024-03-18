package com.sistema_login.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.sistema_login.demo.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);

}