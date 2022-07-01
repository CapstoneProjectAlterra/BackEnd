package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.UserDto;
import com.backend.vaccinebookingsystem.domain.dto.UsernamePassword;
import com.backend.vaccinebookingsystem.service.AuthenticationService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserDto userDto) {
        return authenticationService.register(userDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UsernamePassword.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "/login")
    public ResponseEntity<Object> generateToken(@Valid @RequestBody UsernamePassword usernamePassword) {
        return authenticationService.authenticateUser(usernamePassword);
    }
}
