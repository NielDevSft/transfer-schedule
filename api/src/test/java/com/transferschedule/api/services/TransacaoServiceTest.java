package com.transferschedule.api.services;

import com.transferschedule.api.enums.TIPOOPERACAO;
import com.transferschedule.api.models.*;
import com.transferschedule.api.models.authentication.User;
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
    private AuthenticationService usuarioService;

    @Autowired
    private ContaService contaService;

    private Transacao transacaoValida1;

    @BeforeEach
    public void setUp() {
        User usuario1 = criarUsuario("Daniel Figueiredo", "daniel.silva1313@gmail.com", 1, "938c2cc0dcc05f2b68c4287040cfcf71");
        User usuario2 = criarUsuario("Daniel S Figueiredo", "daniel.jefinho@gmail.com", 1, "938c2cc0dcc05f2b68c4287040cfcf71");

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
        transacaoValida1.setIndTipoOperacao(TIPOOPERACAO.A);
        // Act
        Transacao transacaoCriada = transacaoService.create(transacaoValida1);
        // Assert
        String validUUID = transacaoCriada.getUuid().toString();
        Assertions.assertEquals(UUID.fromString(validUUID).toString(), validUUID);
        Assertions.assertFalse(transacaoCriada.isDeleted());
    }

    private User criarUsuario(String nome, String email, int role, String hashPassword) {
        User usuario = new User();
        usuario.setUsername("user");
        usuario.setPassword("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW");

        return usuario;
    }

    public void should_be_able_to_calculate_taxa_operacao_a() {
        this.transacaoValida1.setIndTipoOperacao(TIPOOPERACAO.A);

        // Act
        Transacao transacaoCriada = transacaoService.create(transacaoValida1);

        // Assertions
        Assertions.assertEquals(transacaoCriada.getDtaTransacao(), transacaoCriada.getDtaCreateAt());
        var valTaxaFixa = BigDecimal.valueOf(3);
        var valTaxaVariavel = transacaoCriada.getNumValTransferencia().divide(BigDecimal.valueOf(0.03));
        Assertions.assertEquals(valTaxaFixa.add(valTaxaVariavel), transacaoCriada.getNumValTaxaPrevista());
    }

    public void should_be_able_to_calculate_taxa_operacao_b() {
        this.transacaoValida1.setIndTipoOperacao(TIPOOPERACAO.B);

        // Act
        Transacao transacaoCriada = transacaoService.create(transacaoValida1);

        // Assertions
        long diferencaMillis = Math.abs(transacaoCriada.getDtaTransacao().getTime() - transacaoCriada.getDtaCreateAt().getTime());
        long diferencaDias = diferencaMillis / (24 * 60 * 60 * 1000);

        Assertions.assertTrue(diferencaDias <= 10);
        var valTaxaFixa = BigDecimal.valueOf(12);
        Assertions.assertEquals(valTaxaFixa, transacaoCriada.getNumValTaxaPrevista());
    }

    public void should_be_able_to_calculate_taxa_operacao_c() {
        this.transacaoValida1.setIndTipoOperacao(TIPOOPERACAO.C);

        // Act
        Transacao transacaoCriada = transacaoService.create(transacaoValida1);
        // Assertions
        long diferencaMillis = Math.abs(transacaoCriada.getDtaTransacao().getTime() - transacaoCriada.getDtaCreateAt().getTime());
        long diferencaDias = diferencaMillis / (24 * 60 * 60 * 1000);
        var valTaxaVariavel = BigDecimal.ZERO;

        if (diferencaDias > 10 && diferencaDias < 20) {
            valTaxaVariavel = transacaoCriada.getNumValTransferencia().divide(BigDecimal.valueOf(0.082));
        } else if (diferencaDias > 20 && diferencaDias < 30) {
            valTaxaVariavel = transacaoCriada.getNumValTransferencia().divide(BigDecimal.valueOf(0.069));
        } else if (diferencaDias > 30 && diferencaDias < 40) {
            valTaxaVariavel = transacaoCriada.getNumValTransferencia().divide(BigDecimal.valueOf(0.047));
        } else {
            valTaxaVariavel = transacaoCriada.getNumValTransferencia().divide(BigDecimal.valueOf(0.017));
        }

        Assertions.assertTrue(valTaxaVariavel.equals(transacaoCriada.getNumValTaxaPrevista()));
    }

    public void should_be_able_to_calculate_taxa_operacao_d() {
        this.transacaoValida1.setIndTipoOperacao(TIPOOPERACAO.D);

        // Define os valores de referência para as faixas de taxação
        BigDecimal valorLimiteA = BigDecimal.valueOf(1000);
        BigDecimal valorLimiteB = BigDecimal.valueOf(2000);

        // Obtém o valor da transferência
        BigDecimal valorTransferencia = transacaoValida1.getNumValTransferencia();

        // Define a taxa inicial
        BigDecimal taxaCalculada;

        // Calcula a taxa com base nos valores especificados
        if (valorTransferencia.compareTo(valorLimiteA) <= 0) {
            // Valor até $1.000 - segue taxação tipo A
            taxaCalculada = valorTransferencia.divide(BigDecimal.valueOf(0.05));
        } else if (valorTransferencia.compareTo(valorLimiteB) <= 0) {
            // Valor entre $1.001 e $2.000 - segue taxação tipo B
            taxaCalculada = valorTransferencia.divide(BigDecimal.valueOf(0.04));
        } else {
            // Valor acima de $2.000 - segue taxação tipo C
            taxaCalculada = valorTransferencia.divide(BigDecimal.valueOf(0.03));
        }

        // Realiza a criação da transação
        Transacao transacaoCriada = transacaoService.create(transacaoValida1);

        // Assertions
        Assertions.assertEquals(taxaCalculada, transacaoCriada.getNumValTaxaPrevista());
    }


    private Cliente criarCliente(User usuario, int categoriaPlano) {
        Cliente cliente = new Cliente();

        cliente.setUsuairo(usuario);
        return cliente;
    }
}
