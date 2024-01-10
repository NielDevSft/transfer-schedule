package com.transferschedule.api.services;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Conta;
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
public class ContaServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContaService contaService;

    private Cliente clienteValido1;
    private Cliente clienteValido2;
    private Conta contaValida1;
    private Conta contaValida2;

    @BeforeEach
    public void setUp() {
        Usuario usuario = criarUsuario("Daniel Figueiredo", "daniel.silva1313@gmail.com", 1, "938c2cc0dcc05f2b68c4287040cfcf71");

        clienteValido1 = criarCliente(usuario, 1);
        clienteValido2 = criarCliente(usuario, 1);

        clienteValido1 = clienteService.create(clienteValido1);
        clienteValido2 = clienteService.create(clienteValido2);

        contaValida1 = new Conta();
        contaValida2 = new Conta();

        contaValida1.setCliente(clienteValido1);
        contaValida2.setCliente(clienteValido2);
    }

    @Test
    public void should_be_able_to_create_a_new_conta() {
        // Act
        var contaCriada = contaService.create(contaValida1);

        // Assert
        String validUUID = contaCriada.getUuid().toString();
        Assertions.assertEquals(UUID.fromString(validUUID).toString(), validUUID);
        Assertions.assertFalse(contaCriada.isDeleted());
    }

    @Test
    public void should_be_able_to_increment_cod_conta() {
        // Act
        var contaCriado1 = contaService.create(contaValida1);
        var contaCriado2 = contaService.create(contaValida2);

        // Assert
        Assertions.assertTrue(contaCriado1.getCodConta() < contaCriado2.getCodConta());
    }

    private Usuario criarUsuario(String nome, String email, int role, String hashPassword) {
        Usuario usuario = new Usuario();
        usuario.setDesNome(nome);
        usuario.setDesEmail(email);
        usuario.setCodRole(role);
        usuario.setHashPassword(hashPassword);

        return usuarioService.create(usuario);
    }

    private Cliente criarCliente(Usuario usuario, int categoriaPlano) {
        Cliente cliente = new Cliente();
        cliente.setCategoriaPlano(categoriaPlano);
        cliente.setUsuairo(usuario);
        return cliente;
    }
}
