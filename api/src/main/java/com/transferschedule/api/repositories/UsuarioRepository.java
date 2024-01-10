package com.transferschedule.api.repositories;

import com.transferschedule.api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepositoryInterface extends JpaRepository<Usuario, UUID>
{
}
