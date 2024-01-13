package com.transferschedule.api.services;

import com.transferschedule.api.dtos.UserLogged;
import com.transferschedule.api.models.authentication.Authority;
import com.transferschedule.api.models.authentication.User;
import com.transferschedule.api.repositories.AuthorityRepository;
import com.transferschedule.api.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    public User create(User usuario) {
        var existing = this.userRepository.findByUsername(usuario.getUsername());
        return existing.orElseGet(() -> this.userRepository.save(usuario));
    }

    public Optional<User> findUserById(long id){
        return userRepository.findById(id);
    }

    public UserLogged login(User user) {
        var existing = this.userRepository.findByUsername(user.getUsername());
        if (existing.isPresent()) {
            var aut = new Authority();
            aut.setUsername(user.getUsername());
            var usr = userRepository.findByUsername(aut.getUsername());
            usr.ifPresent(u -> u.setPassword(""));
            Example<Authority> example = Example.of(aut);

            return new UserLogged(usr, authorityRepository.findAll(example));
        }
        throw new AccessDeniedException("Falha na autenticação. Credenciais inválidas.");
    }

    public Authority create(Authority authority) {
        return this.authorityRepository.save(authority);
    }
}
