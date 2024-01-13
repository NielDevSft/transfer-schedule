package com.transferschedule.api.controllers;

import com.transferschedule.api.models.Cliente;
import com.transferschedule.api.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cliente", produces = {"application/json"})
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @GetMapping(path = "/all-by-user/{id}")
    public ResponseEntity<Cliente> getByUserId(@PathVariable("id") String id){
       var cliExisting= clienteService.findByUsuarioId(Long.valueOf(id));
       if(cliExisting.isPresent()) {
           return new ResponseEntity<Cliente>(cliExisting.get(), HttpStatus.OK);
       }
       else {
           return new ResponseEntity<Cliente>(HttpStatus.BAD_REQUEST);
       }
    }
}
