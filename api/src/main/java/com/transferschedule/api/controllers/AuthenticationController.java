package com.transferschedule.api.controllers;

import com.transferschedule.api.dtos.UserLogged;
import com.transferschedule.api.models.authentication.Authority;
import com.transferschedule.api.models.authentication.User;
import com.transferschedule.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/authentication", produces = {"application/json"})
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLogged> login(@RequestHeader("Authorization") String authorizationHeader) {
        String base64Credentials = authorizationHeader.substring("Basic".length()).trim();
        String credentials = new String(java.util.Base64.getDecoder().decode(base64Credentials));
        String[] parts = credentials.split(":", 2);
        String username = parts[0];
        var user = new User();
        user.setUsername(username);
        var auts = this.authenticationService.login(user);
        return new ResponseEntity<UserLogged>(auts, HttpStatus.OK);

    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Long> getAll() {
        var li = List.of(Long.valueOf(1));
        return li;

    }
}
