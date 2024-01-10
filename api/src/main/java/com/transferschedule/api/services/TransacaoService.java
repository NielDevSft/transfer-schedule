package com.transferschedule.api.services;

import com.transferschedule.api.models.Transacao;
import com.transferschedule.api.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {
    @Autowired
    TransacaoRepository transacaoRepository;

    public Transacao create(Transacao transacao) {
        transacao.isValid();
        return transacaoRepository.save((transacao));
    }
}
