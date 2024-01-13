package com.transferschedule.api.controllers;

import com.transferschedule.api.dtos.ClienteDTO;
import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.models.Conta;
import com.transferschedule.api.services.AuthenticationService;
import com.transferschedule.api.services.ClienteService;
import com.transferschedule.api.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/conta", produces = {"application/json"})
public class ContaController {
    @Autowired
    private ContaService contaService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ClienteService clienteService;

    @PostMapping(path = "/primeira-conta",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Conta> createPrimeiraConta(@RequestBody ClienteDTO cliente) {
        var userExisting = authenticationService
                .findUserById(cliente.idUsuario());
        var cliToPersist = new Cliente();
        cliToPersist.setUsuairo(userExisting.get());
        var clientExisting = clienteService.create(cliToPersist);
        var newConta = new Conta();
        newConta.setCliente(clientExisting);

        return new ResponseEntity<Conta>(contaService.create(newConta), HttpStatus.CREATED);
    }
}
