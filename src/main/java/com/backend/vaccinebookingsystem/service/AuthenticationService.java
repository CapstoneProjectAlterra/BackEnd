package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDetailsDao;
import com.backend.vaccinebookingsystem.domain.dto.*;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;
import com.backend.vaccinebookingsystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private FamilyRepository familyRepository;

    public ResponseEntity<Object> register(UserDto userDto) {
        try {
            log.info("Creating a new user");

            Optional<UserDao> optionalUserDao = userRepository.findByUsername(userDto.getUsername());

            if (optionalUserDao.isPresent()) {
                log.info("Username already exists");
                return ResponseUtil.build(AppConstant.ResponseCode.ALREADY_EXISTS, null, HttpStatus.CONFLICT);
            }

            UserDao userDao = UserDao.builder()
                    .username(userDto.getUsername())
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .profile(ProfileDao.builder()
                            .role(userDto.getProfile().getRole())
                            .build())
                    .build();

            userRepository.save(userDao);

            Optional<ProfileDao> optionalProfileDao = profileRepository.findById(userDao.getProfile().getUserId());

            log.info("Creating Some Family Data when register");
            FamilyDao familyDao = FamilyDao.builder()
                    .NIK(userDto.getUsername())
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .profile(optionalProfileDao.get())
                    .build();

            familyRepository.save(familyDao);

            UserDto dto = UserDto.builder()
                    .id(userDao.getId())
                    .username(userDao.getUsername())
                    .name(userDao.getName())
                    .email(userDao.getEmail())
                    .password(userDao.getPassword())
                    .profile(ProfileDto.builder()
                            .userId(userDao.getProfile().getUserId())
                            .role(userDao.getProfile().getRole())
                            .build())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating a new User. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> authenticateUser(UsernamePassword usernamePassword) {
        try {
            log.info("Generating JWT token");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usernamePassword.getUsername(), usernamePassword.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenProvider.generateJwtToken(authentication);

            UserDetailsDao userDetailsDao = (UserDetailsDao) authentication.getPrincipal();

            List<String> roles = userDetailsDao.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            JwtResponse tokenResponse = JwtResponse.builder()
                    .userId(userDetailsDao.getId())
                    .username(usernamePassword.getUsername())
                    .token(jwt)
                    .roles(roles)
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, tokenResponse, HttpStatus.OK);
        } catch (Exception badCredentialsException) {
//            throw new RuntimeException(badCredentialsException.getMessage(), badCredentialsException);
            log.error("An error occurred in Authenticate User. Error {}", badCredentialsException.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
        }
    }
}
