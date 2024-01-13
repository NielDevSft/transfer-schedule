package com.transferschedule.api.repositories;

import com.transferschedule.api.models.authentication.User;
import com.transferschedule.api.models.authentication.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Autowired
private JpaRepository<User, Long> userRepository;
@Autowired
private JpaRepository<UserRole, Long> userRoleRepository;


public interface UsuarioRepository
{

}
