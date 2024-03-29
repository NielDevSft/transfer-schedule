package com.transferschedule.api.controllers;

import com.transferschedule.api.dtos.ClienteDTO;
import com.transferschedule.api.dtos.ContaDTO;
import com.transferschedule.api.dtos.Converter;
import com.transferschedule.api.models.Conta;
import com.transferschedule.api.services.AuthenticationService;
import com.transferschedule.api.services.ClienteService;
import com.transferschedule.api.services.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/conta", produces = {"application/json"})
@CrossOrigin(origins = "http://localhost:4200")
public class ContaController {
    @Autowired
    private ContaService contaService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private ClienteService clienteService;

    @GetMapping(path = "/get-by-cod/{id}")
    public ResponseEntity<ContaDTO> getByCod(@PathVariable("id") String id) {
        var conExisting = this.contaService.findByCod(Long.valueOf(id));
        if (conExisting.isPresent()) {
            var contaDTO = Converter.convertContaToDTO(conExisting.get());
            return new ResponseEntity<ContaDTO>(contaDTO, HttpStatus.OK);
        }
        return new ResponseEntity<ContaDTO>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/all-by-cliente/{uuid}")
    public ResponseEntity<List<ContaDTO>> getByCodCliente(@PathVariable("uuid") String uuid) {
        var conExisting = this.contaService.findAllByUuidCliente(uuid);
        if (!conExisting.isEmpty()) {
            var listContaDTO = new ArrayList<ContaDTO>();
            conExisting.forEach(conta -> {
                listContaDTO.add(Converter.convertContaToDTO(conta));
            });
            return new ResponseEntity<List<ContaDTO>>(listContaDTO, HttpStatus.OK);
        }
        return new ResponseEntity<List<ContaDTO>>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/primeira-conta",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContaDTO> createPrimeiraConta(@RequestBody ClienteDTO cliente) {
        var cliToPersist = Converter.convertDTOToCliente(cliente);
        var userExisting = authenticationService.findUserById(cliToPersist.getUsuairo().getId());
        cliToPersist.setUsuairo(userExisting.get());
        var clientExisting = clienteService.create(cliToPersist);
        var newConta = new Conta();
        newConta.setCliente(clientExisting);

        var conta = contaService.create(newConta);
        return new ResponseEntity<ContaDTO>(Converter.convertContaToDTO(conta), HttpStatus.CREATED);
    }
    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContaDTO> createConta(@RequestBody ClienteDTO cliente) {
        var clientExisting = clienteService.findByUsuarioId(cliente.usuairo().id());
        var newConta = new Conta();
        if(clientExisting.isPresent()) {
            newConta.setCliente(clientExisting.get());
            var conta = contaService.create(newConta);
            return new ResponseEntity<ContaDTO>(Converter.convertContaToDTO(conta), HttpStatus.CREATED);
        }
        return new ResponseEntity<ContaDTO>(HttpStatus.BAD_REQUEST);

    }
}
