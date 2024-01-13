package com.transferschedule.api.dtos;

import com.transferschedule.api.models.authentication.Authority;
import com.transferschedule.api.models.authentication.User;

import java.util.List;
import java.util.Optional;

public record UserLogged(Optional<User> user, List<Authority> authority) {
}
