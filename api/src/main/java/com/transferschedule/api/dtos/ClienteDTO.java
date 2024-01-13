package com.transferschedule.api.dtos;

import com.transferschedule.api.models.authentication.User;

import java.util.Date;

public record ClienteDTO(
        String desNome,
        String desCpf,
        String numTelefo,
        Long idUsuario,
        User usuario,
        Date dtaNascimento

) {

}