package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.UserDto;
import com.backend.vaccinebookingsystem.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('HEALTH_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> updateUserById(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUserById(id, userDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
