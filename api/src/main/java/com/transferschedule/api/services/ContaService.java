package com.transferschedule.api.services;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Conta;
import com.transferschedule.api.repositories.ClienteRepository;
import com.transferschedule.api.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    public Conta create(Conta conta){
        conta.isValid();
        var lastCreated = this.findLastCreate();
        conta.setCodConta(1);

        lastCreated.ifPresent(cli -> {
            conta.setCodConta(cli.getCodConta() + 1);
        });

        return this.contaRepository.save(conta);
    }


    public Optional<Conta> findByCod(long cod){
        var conToSearch = new Conta();
        conToSearch.setCodConta(cod);
        Example<Conta> example = Example.of(conToSearch);

        return Optional.of(this.contaRepository.findBy(example, c -> {
            return c.firstValue();
        }));
    }

    public List<Conta> findAllByUuidCliente(String uuid) {
        var contaList = contaRepository.findAllByClienteUuid(UUID.fromString(uuid));
        return contaList;
    }
    public Optional<Conta> findById(UUID uuid){
        return this.contaRepository.findById(uuid);
    }

    public Optional<Conta> findLastCreate(){
        return this.contaRepository.findFirstByOrderByDtaCreateAtDesc();
    }
}
