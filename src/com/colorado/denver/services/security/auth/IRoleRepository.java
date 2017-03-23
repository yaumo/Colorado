package com.colorado.denver.services.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.colorado.denver.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
}
