package com.colorado.denver.services.persistence.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.colorado.denver.model.User;

@Service
@Configuration(value = "userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByusername(String username);

	@Override
	void delete(User user);

}