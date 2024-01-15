package com.transferschedule.api.dtos;

import com.transferschedule.api.enums.TIPOOPERACAO;

import java.math.BigDecimal;
import java.util.Date;

public record TransacaoDTO(
        String uuid,
        long codTransacao,
        BigDecimal numValTransferencia,
        BigDecimal numValTaxaPrevista,
        Date dtaTransacao,
        TIPOOPERACAO indTipoOperacao,
        String contaOrigemUuid,
        String contaDestinoUuid,
        String clienteResponsavelUuid,
        Date dtaCreateAt,
        Date dtaUpdateAt,
        Date dtaDeleteAt
        ) {
    // You can add additional methods or validations if needed
}