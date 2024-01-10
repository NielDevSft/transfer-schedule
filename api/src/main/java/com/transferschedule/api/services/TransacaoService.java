package com.transferschedule.api.services;

import com.transferschedule.api.enums.TIPOOPERACAO;
import com.transferschedule.api.models.Transacao;
import com.transferschedule.api.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class TransacaoService {
    @Autowired
    TransacaoRepository transacaoRepository;

    public Transacao create(Transacao transacao) {
        LocalDate hoje = LocalDate.now();
        transacao.setDtaTransacao(new Date());
        long diferencaDias = calcularDiferencaDias(transacao.getDtaTransacao(), hoje);

        var tipoOperacao = calcularTipoOperacao(transacao, diferencaDias)
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma taxa aplicável"));

        switch (tipoOperacao) {
            case A:
                calcularTaxaTipoA(transacao, diferencaDias);
                break;
            case B:
                calcularTaxaTipoB(transacao, diferencaDias);
                break;
            case C:
                calcularTaxaTipoC(transacao, diferencaDias);
                break;
            case D:
                calcularTaxaTipoD(transacao);
                break;
            default:
                break;
        }

        return transacaoRepository.save(transacao);
    }

    private Optional<TIPOOPERACAO> calcularTipoOperacao(Transacao transacao, long diferencaDias) {
        if (diferencaDias == 0) {
            transacao.setIndTipoOperacao(TIPOOPERACAO.A);
        } else if (diferencaDias <= 10) {
            transacao.setIndTipoOperacao(TIPOOPERACAO.B);
        } else if (diferencaDias <= 20) {
            transacao.setIndTipoOperacao(TIPOOPERACAO.C);
            calcularTaxaTipoC(transacao, diferencaDias);
        } else if (diferencaDias <= 30) {
            transacao.setIndTipoOperacao(TIPOOPERACAO.C);
            calcularTaxaTipoC(transacao, diferencaDias);
        } else if (diferencaDias <= 40) {
            transacao.setIndTipoOperacao(TIPOOPERACAO.C);
            calcularTaxaTipoC(transacao, diferencaDias);
        } else {
            transacao.setIndTipoOperacao(TIPOOPERACAO.D);
            calcularTaxaTipoD(transacao);
        }

        return Optional.ofNullable(transacao.getIndTipoOperacao());
    }
    private long calcularDiferencaDias(Date dataTransferencia, LocalDate hoje) {
        LocalDate dataTransferenciaLocalDate = dataTransferencia.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(dataTransferenciaLocalDate, hoje);
    }

    private void calcularTaxaTipoA(Transacao transacao, long diferencaDias) {
        if (diferencaDias == 0) {
            BigDecimal taxaFixa = BigDecimal.valueOf(3);
            BigDecimal taxaVariavel = transacao.getNumValTransferencia().multiply(BigDecimal.valueOf(0.03));
            BigDecimal taxa = taxaFixa.add(taxaVariavel);
            transacao.setNumValTaxaPrevista(taxa);
        }
    }

    private void calcularTaxaTipoB(Transacao transacao, long diferencaDias) {
        if (diferencaDias <= 10) {
            BigDecimal taxaFixa = BigDecimal.valueOf(12);
            transacao.setNumValTaxaPrevista(taxaFixa);
        }
    }

    private void calcularTaxaTipoC(Transacao transacao, long diferencaDias) {
        BigDecimal taxaVariavel = BigDecimal.ZERO;
        if (diferencaDias > 10 && diferencaDias <= 20) {
            taxaVariavel = transacao.getNumValTransferencia().multiply(BigDecimal.valueOf(0.082));
        } else if (diferencaDias > 20 && diferencaDias <= 30) {
            taxaVariavel = transacao.getNumValTransferencia().multiply(BigDecimal.valueOf(0.069));
        } else if (diferencaDias > 30 && diferencaDias <= 40) {
            taxaVariavel = transacao.getNumValTransferencia().multiply(BigDecimal.valueOf(0.047));
        } else {
            taxaVariavel = transacao.getNumValTransferencia().multiply(BigDecimal.valueOf(0.017));
        }
        transacao.setNumValTaxaPrevista(taxaVariavel);
    }

    private void calcularTaxaTipoD(Transacao transacao) {
        BigDecimal valorTransferencia = transacao.getNumValTransferencia();
        BigDecimal taxaCalculada;

        BigDecimal limiteA = BigDecimal.valueOf(1000);
        BigDecimal limiteB = BigDecimal.valueOf(2000);

        if (valorTransferencia.compareTo(limiteA) <= 0) {
            taxaCalculada = valorTransferencia.multiply(BigDecimal.valueOf(0.03)).add(BigDecimal.valueOf(3));
        } else if (valorTransferencia.compareTo(limiteB) <= 0) {
            taxaCalculada = BigDecimal.valueOf(12);
        } else {
            taxaCalculada = valorTransferencia.multiply(BigDecimal.valueOf(0.03));
        }

        transacao.setNumValTaxaPrevista(taxaCalculada);
    }
}
