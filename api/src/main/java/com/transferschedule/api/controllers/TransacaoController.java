package com.transferschedule.api.controllers;

import com.transferschedule.api.dtos.ContaDTO;
import com.transferschedule.api.dtos.Converter;
import com.transferschedule.api.dtos.TransacaoDTO;
import com.transferschedule.api.models.Transacao;
import com.transferschedule.api.services.ClienteService;
import com.transferschedule.api.services.ContaService;
import com.transferschedule.api.services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/transacao")
public class TransacaoController {
    @Autowired
    TransacaoService transacaoService;
    @Autowired
    ContaService contaService;
    @Autowired
    ClienteService clienteService;


    @GetMapping(path = "/all-by-cliente-responsavel/{uuid}")
    public @ResponseBody ResponseEntity<List<TransacaoDTO>> getAll(@PathVariable("uuid") String uuid) {
        var transacaoList = transacaoService.findAllByClienteUuid(UUID.fromString(uuid));
        if(!transacaoList.isEmpty()){
            var listTransacaoDTO = new ArrayList<TransacaoDTO>();
            transacaoList.forEach(tra -> {
                listTransacaoDTO.add(Converter.convertTransacaoToDTO(tra));
            });
            return new ResponseEntity<List<TransacaoDTO>>(listTransacaoDTO, HttpStatus.OK);
        }
        return new ResponseEntity<List<TransacaoDTO>>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/nova-transacao",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<TransacaoDTO> novaTransacao(@RequestBody TransacaoDTO transacaoDTO,
                                                      @RequestHeader("Authorization") String authorizationHeader) {
        String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
        String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials));
        String[] parts = credentials.split(":", 2);
        String username = parts[0];

        var clienteResponsavel = clienteService.findFirstByUsuairoUsername(username);
        var transacaoToPersist = Converter.convertDTOToTransacao(
                transacaoDTO,
                null,
                null,
                null);
        var contaDestino = contaService.findById(UUID.fromString(transacaoDTO.contaDestinoUuid()));
        var contaOrigem = contaService.findById(UUID.fromString(transacaoDTO.contaOrigemUuid()));

        transacaoToPersist.setContaDestino(contaDestino.orElseThrow());
        transacaoToPersist.setContaOrigem(contaOrigem.orElseThrow());
        transacaoToPersist.setCliente(clienteResponsavel.orElseThrow());

        var transacaoCriada =transacaoService.create(transacaoToPersist);
        var resp = Converter.convertTransacaoToDTO(transacaoCriada);

        return new ResponseEntity<TransacaoDTO>(resp, HttpStatus.CREATED);

    }
}
