package com.transferschedule.api.services;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public Cliente create(Cliente cliente){
        cliente.isValid();

        var lastCreated = this.findLastCreate();
        cliente.setCodCliente(1);

        lastCreated.ifPresent(cli ->
            cliente.setCodCliente(cli.getCodCliente() + 1
        ));

        return this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> findByUsuarioId(long id){
        return clienteRepository.findFirstByUsuairoId(id);
    }
    public Optional<Cliente> findLastCreate(){
        return this.clienteRepository.findFirstByOrderByDtaCreateAtDesc();
    }
}
