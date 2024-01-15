package com.transferschedule.api.dtos;

import java.util.Date;

public record ContaDTO(
        String uuid,
        boolean deleted,
        Date dtaCreateAt,
        Date dtaUpdateAt,
        Date dtaDeleteAt,
        Long codConta,
        ClienteDTO cliente
) {
}
