package com.transferschedule.api.dtos;

import com.transferschedule.api.models.authentication.User;

import java.util.Date;

public record ClienteDTO(
        String uuid,
        Date dtaCreateAt,
        Date dtaUpdateAt,
        long codCliente,
        String desNomeCompleto,
        String numTelefone,
        String desCpf,
        Date dtaNascimento,
        UsuarioDTO usuairo
) {
}

