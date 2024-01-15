package com.transferschedule.api.dtos;

import com.transferschedule.api.enums.TIPOOPERACAO;
import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Conta;
import com.transferschedule.api.models.Transacao;
import com.transferschedule.api.models.authentication.User;

import java.util.Date;
import java.util.UUID;

public class Converter {

    public static ClienteDTO convertClienteToDTO(Cliente cliente) {
        return new ClienteDTO(
                cliente.getUuid().toString(),
                cliente.getDtaCreateAt(),
                cliente.getDtaUpdateAt(),
                cliente.getCodCliente(),
                cliente.getDesNomeCompleto(),
                cliente.getDesCpf(),
                cliente.getDtaNascimento(),
                convertUserToDTO(cliente.getUsuairo())
        );
    }

    public static Cliente convertDTOToCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        if (clienteDTO.uuid() != null) {
            cliente.setUuid(UUID.fromString(clienteDTO.uuid()));
        }
        cliente.setDtaCreateAt(clienteDTO.dtaCreateAt());
        cliente.setDtaUpdateAt(clienteDTO.dtaUpdateAt());
        cliente.setCodCliente(clienteDTO.codCliente());
        cliente.setDesNomeCompleto(clienteDTO.desNomeCompleto());
        cliente.setDesCpf(clienteDTO.desCpf());
        cliente.setDtaNascimento(clienteDTO.dtaNascimento());
        cliente.setUsuairo(convertDTOToUser(clienteDTO.usuairo()));
        return cliente;
    }

    public static UsuarioDTO convertUserToDTO(User user) {
        return new UsuarioDTO(
                user.getId(),
                user.getUsername(),
                user.isEnabled()
        );
    }

    public static User convertDTOToUser(UsuarioDTO usuarioDTO) {
        User user = new User();
        user.setId(usuarioDTO.id());
        user.setUsername(usuarioDTO.username());
        user.setEnabled(usuarioDTO.enabled());
        return user;
    }

    public static ContaDTO convertContaToDTO(Conta conta) {
        return new ContaDTO(
                conta.getUuid().toString(),
                conta.isDeleted(),
                conta.getDtaCreateAt(),
                conta.getDtaUpdateAt(),
                conta.getDtaDeleteAt(),
                conta.getCodConta(),
                convertClienteToDTO(conta.getCliente())
        );
    }

    public static Conta convertDTOToConta(ContaDTO contaDTO) {
        Conta conta = new Conta();
        conta.setUuid(UUID.fromString(contaDTO.uuid()));
        conta.setDeleted(contaDTO.deleted());
        conta.setDtaCreateAt(contaDTO.dtaCreateAt());
        conta.setDtaUpdateAt(contaDTO.dtaUpdateAt());
        conta.setDtaDeleteAt(contaDTO.dtaDeleteAt());
        conta.setCodConta(contaDTO.codConta());
        conta.setCliente(convertDTOToCliente(contaDTO.cliente()));
        return conta;
    }

    public static TransacaoDTO convertTransacaoToDTO(Transacao transacao) {
        return new TransacaoDTO(
                transacao.getUuid().toString(),
                transacao.getCodTransacao(),
                transacao.getNumValTransferencia(),
                transacao.getNumValTaxaPrevista(),
                transacao.getDtaTransacao(),
                transacao.getIndTipoOperacao(),
                convertContaToDTO(transacao.getContaOrigem()).uuid(),
                convertContaToDTO(transacao.getContaDestino()).uuid(),
                transacao.getCliente().getUuid().toString(),
                transacao.getDtaCreateAt(),
                transacao.getDtaUpdateAt(),
                transacao.getDtaDeleteAt()
        );
    }

    public static Transacao convertDTOToTransacao(TransacaoDTO transacaoDTO,
                                                  ContaDTO[] contaOrigem,
                                                  ContaDTO[] contaDestino,
                                                  ClienteDTO[] clienteResponsavel) {
        var transacao = new Transacao();
        transacao.setUuid(UUID.fromString(transacaoDTO.uuid()));
        transacao.setCodTransacao(transacaoDTO.codTransacao());
        transacao.setNumValTransferencia(transacaoDTO.numValTransferencia());
        transacao.setNumValTaxaPrevista(transacaoDTO.numValTaxaPrevista());
        transacao.setDtaTransacao(transacaoDTO.dtaTransacao());
        transacao.setIndTipoOperacao(transacaoDTO.indTipoOperacao());
        transacao.setContaOrigem((contaOrigem.length > 0)? convertDTOToConta(contaOrigem[0]): null);
        transacao.setContaDestino((contaDestino.length > 0)? convertDTOToConta(contaDestino[0]): null);
        transacao.setCliente(convertDTOToCliente((clienteResponsavel.length > 0)?clienteResponsavel[0]: null));
        transacao.setDtaCreateAt(transacaoDTO.dtaCreateAt());
        transacao.setDtaUpdateAt(transacaoDTO.dtaUpdateAt());
        transacao.setDtaDeleteAt(transacaoDTO.dtaDeleteAt());

        return transacao;
    }


}