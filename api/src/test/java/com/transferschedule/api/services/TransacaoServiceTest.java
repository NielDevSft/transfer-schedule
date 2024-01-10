package com.transferschedule.api.services;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Conta;
import com.transferschedule.api.models.Transacao;
import com.transferschedule.api.models.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TransacaoServiceTest {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContaService contaService;

    private Transacao transacaoValida1;

    @BeforeEach
    public void setUp() {
        Usuario usuario1 = criarUsuario("Daniel Figueiredo", "daniel.silva1313@gmail.com", 1, "938c2cc0dcc05f2b68c4287040cfcf71");
        Usuario usuario2 = criarUsuario("Daniel S Figueiredo", "daniel.jefinho@gmail.com", 1, "938c2cc0dcc05f2b68c4287040cfcf71");

        usuario1 = usuarioService.create(usuario1);
        usuario2 = usuarioService.create(usuario2);

        Cliente cliente1 = criarCliente(usuario1, 1);
        Cliente cliente2 = criarCliente(usuario2, 1);

        cliente1 = clienteService.create(cliente1);
        cliente2 = clienteService.create(cliente2);

        Conta conta1 = new Conta();
        Conta conta2 = new Conta();
        conta1.setCliente(cliente1);
        conta2.setCliente(cliente2);

        conta1 = contaService.create(conta1);
        conta2 = contaService.create(conta2);

        transacaoValida1 = new Transacao();
        transacaoValida1.setNumValTransferencia(BigDecimal.valueOf(111));
        transacaoValida1.setNumValTaxaPrevista(BigDecimal.valueOf(111));
        transacaoValida1.setDtaTransacao(new Date());
        transacaoValida1.setContaOrigem(conta1);
        transacaoValida1.setContaDestino(conta2);
        transacaoValida1.setCliente(cliente1);
    }

    @Test
    public void should_be_able_to_create_a_new_transacao() {
        // Act
        Transacao transacaoCriada = transacaoService.create(transacaoValida1);
        // Assert
        String validUUID = transacaoCriada.getUuid().toString();
        Assertions.assertEquals(UUID.fromString(validUUID).toString(), validUUID);
        Assertions.assertFalse(transacaoCriada.isDeleted());
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
