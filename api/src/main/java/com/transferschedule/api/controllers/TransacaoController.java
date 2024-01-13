package com.transferschedule.api.controllers;

import com.transferschedule.api.models.Transacao;
import com.transferschedule.api.services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/transacao")
public class TransacaoController {
    @Autowired
    TransacaoService transacaoService;


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Transacao> getAll() {

        return transacaoService.findAll();
    }
}
