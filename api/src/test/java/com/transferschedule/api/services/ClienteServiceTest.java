package com.transferschedule.api.services;

import com.transferschedule.api.models.Cliente;
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
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuarioValido1;
    private Usuario usuarioValido2;

    @BeforeEach
    public void setUp() {
        usuarioValido1 = criarUsuario("Daniel Figueiredo", "daniel.silva1313@gmail.com", 1, "938c2cc0dcc05f2b68c4287040cfcf71");
        usuarioValido2 = criarUsuario("Daniel S Figueiredo", "daniel.jefinho@gmail.com", 1, "938c2cc0dcc05f2b68c4287040cfcf71");

        usuarioValido1 = usuarioService.create(usuarioValido1);
        usuarioValido2 = usuarioService.create(usuarioValido2);
    }

    @Test
    public void should_be_able_to_create_a_new_cliente() {
        // Arrange
        Cliente clienteValido = criarCliente(usuarioValido1, 1);

        // Act
        var clienteCriado = clienteService.create(clienteValido);

        // Assert
        String validUUID = clienteCriado.getUuid().toString();
        Assertions.assertEquals(UUID.fromString(validUUID).toString(), validUUID);
        Assertions.assertTrue(clienteCriado.getCodCliente() > 0);
        Assertions.assertFalse(clienteCriado.isDeleted());
    }

    @Test
    public void should_be_able_to_increment_cod_cliente() {
        // Arrange
        Cliente clienteValido1 = criarCliente(usuarioValido1, 1);
        Cliente clienteValido2 = criarCliente(usuarioValido2, 1);

        // Act
        var clienteCriado1 = clienteService.create(clienteValido1);
        var clienteCriado2 = clienteService.create(clienteValido2);

        // Assert
        Assertions.assertTrue(clienteCriado1.getCodCliente() < clienteCriado2.getCodCliente());
    }

    @Test
    public void should_throw_error_usuario_is_not_valid() {
        // Arrange
        Cliente clienteInvalido = criarCliente(null, 1);

        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clienteService.create(clienteInvalido);
        });

        // assertions
        Assertions.assertEquals("O usuario é obrigatório para a criação de clientes", exception.getMessage());
    }

    private Usuario criarUsuario(String nome, String email, int role, String hashPassword) {
        Usuario usuario = new Usuario();
        usuario.setDesNome(nome);
        usuario.setDesEmail(email);
        usuario.setCodRole(role);
        usuario.setHashPassword(hashPassword);
        return usuario;
    }

    private Cliente criarCliente(Usuario usuario, int categoriaPlano) {
        Cliente cliente = new Cliente();
        cliente.setCategoriaPlano(categoriaPlano);
        cliente.setUsuairo(usuario);
        return cliente;
    }
}
