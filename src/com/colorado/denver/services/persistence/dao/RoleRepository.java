package com.colorado.denver.services.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.colorado.denver.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRoleName(String name);

	@Override
	void delete(Role role);

}
