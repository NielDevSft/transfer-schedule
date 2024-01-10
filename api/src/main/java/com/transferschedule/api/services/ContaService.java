package com.transferschedule.api.services;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Conta;
import com.transferschedule.api.repositories.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    public Conta create(Conta conta){
        conta.isValid();


        var lastCreated = this.findLastCreate();
        conta.setCodConta(1);

        lastCreated.ifPresent(cli ->
                conta.setCodConta(cli.getCodConta() + 1
                ));
        
        return this.contaRepository.save(conta);
    }

    public Optional<Conta> findLastCreate(){
        return this.contaRepository.findFirstByOrderByDtaCreateAtDesc();
    }
}
