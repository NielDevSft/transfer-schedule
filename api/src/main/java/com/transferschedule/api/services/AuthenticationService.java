package com.transferschedule.api.services;

import com.transferschedule.api.models.authentication.User;
import com.transferschedule.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    public User create(User usuario) {
        return this.usuarioRepository.save(usuario);
    }
}
