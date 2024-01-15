package com.transferschedule.api.dtos;

public record UsuarioDTO(
        long id,
        String username,
        boolean enabled
) {
}
