package com.colorado.denver.services.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colorado.denver.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}