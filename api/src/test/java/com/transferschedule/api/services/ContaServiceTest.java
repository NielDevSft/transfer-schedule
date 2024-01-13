package com.transferschedule.api.services;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Conta;
import com.transferschedule.api.models.authentication.User;
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
    private AuthenticationService usuarioService;

    @Autowired
    private ContaService contaService;

    private Cliente clienteValido1;
    private Cliente clienteValido2;
    private Conta contaValida1;
    private Conta contaValida2;

    @BeforeEach
    public void setUp() {
        User usuario1 = criarUsuario("daniel.silva1313@gmail.com", "{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW");
        User usuario2 = criarUsuario("daniel.silva1213@gmail.com", "{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW");

        clienteValido1 = criarCliente(usuario1, 1);
        clienteValido2 = criarCliente(usuario2, 1);

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

    private User criarUsuario(String email, String senha) {
        User usuario = new User();
        usuario.setUsername(email);
        usuario.setPassword(senha);

        return usuarioService.create(usuario);
    }

    private Cliente criarCliente(User usuario, int categoriaPlano) {
        Cliente cliente = new Cliente();

        cliente.setUsuairo(usuario);
        return clienteService.create(cliente);
    }
}
