package com.transferschedule.api.services;

import com.transferschedule.api.models.Usuario;
import com.transferschedule.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UsuarioService{
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario create(Usuario usuario) {
        usuario.isValid();
        return this.usuarioRepository.save(usuario);
    }
}
