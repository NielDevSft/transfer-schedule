package com.transferschedule.api.controllers;

import com.transferschedule.api.dtos.ClienteDTO;
import com.transferschedule.api.dtos.Converter;
import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/cliente", produces = {"application/json"})
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping(path = "/all-by-user/{id}")
    public ResponseEntity<ClienteDTO> getByUserId(@PathVariable("id") String id) {
        var cliExisting = clienteService.findByUsuarioId(Long.valueOf(id));
        if(cliExisting.isPresent()){
            var cliDTO = Converter.convertClienteToDTO(cliExisting.get());
            return new ResponseEntity<ClienteDTO>(cliDTO, HttpStatus.OK);
        }
        return new ResponseEntity<ClienteDTO>(HttpStatus.BAD_REQUEST);
    }
}
