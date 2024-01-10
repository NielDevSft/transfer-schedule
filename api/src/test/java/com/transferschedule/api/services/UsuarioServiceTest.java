package com.transferschedule.api.services;

import com.transferschedule.api.models.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuarioInvalido;
    private Usuario usuarioValido;

    @BeforeEach
    public void setUp() {
        usuarioValido = criarUsuario("Daniel Figueiredo", "daniel.silva1313@gmail.com", 1, "938c2cc0dcc05f2b68c4287040cfcf71");
        usuarioInvalido = criarUsuario("Daniel Figueiredo", "daniel.silva1313@gmail", 1, "a40296c70e");
    }

    @Test
    public void should_be_able_to_create_a_new_usuario() {
        // Act
        var usuarioCriado = usuarioService.create(usuarioValido);

        // Assert
        String validUUID = usuarioCriado.getUuid().toString();
        Assertions.assertEquals(UUID.fromString(validUUID).toString(), validUUID);
        Assertions.assertFalse(usuarioCriado.isDeleted());
    }

    @Test
    public void should_throw_error_email_is_not_valid() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.create(usuarioInvalido);
        }, "Formato de e-mail não compatível");
    }

    @Test
    public void should_throw_error_hash_is_not_valid() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.create(usuarioInvalido);
        }, "Hash de senha não compatível");
    }

    private Usuario criarUsuario(String nome, String email, int role, String hashPassword) {
        Usuario usuario = new Usuario();
        usuario.setDesNome(nome);
        usuario.setDesEmail(email);
        usuario.setCodRole(role);
        usuario.setHashPassword(hashPassword);
        return usuario;
    }
}
