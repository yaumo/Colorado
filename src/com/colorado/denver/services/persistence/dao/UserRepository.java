package com.colorado.denver.services.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colorado.denver.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByusername(String username);

	@Override
	void delete(User user);

}