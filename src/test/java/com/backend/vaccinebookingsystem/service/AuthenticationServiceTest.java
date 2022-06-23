package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDetailsDao;
import com.backend.vaccinebookingsystem.domain.dto.*;
import com.backend.vaccinebookingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AuthenticationService.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test
    void registerException_Test() {
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        ResponseEntity<Object> actualRegisterResult = authenticationService.register(new UserDto());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualRegisterResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualRegisterResult.getBody()).getData());

        ApiResponseStatus status = ((ApiResponse<Object>) actualRegisterResult.getBody()).getStatus();
        assertEquals("Unknown Error", status.getMessage());
        assertEquals("UNKNOWN_ERROR", status.getCode());

        verify(passwordEncoder).encode((CharSequence) any());
    }

    @Test
    void registerSuccess_Test() {
        UserDao userDao2 = new UserDao();
        userDao2.setBookingDaoList(new ArrayList<>());
        userDao2.setId(123L);
        userDao2.setPassword("iloveyou");
        userDao2.setProfile(new ProfileDao());

        ProfileDao profileDao2 = new ProfileDao();
        profileDao2.setFamilyDaoList(new ArrayList<>());
        profileDao2.setHealthFacilityDaoList(new ArrayList<>());
        profileDao2.setRole(AppConstant.ProfileRole.USER);
        profileDao2.setUser(userDao2);
        profileDao2.setUserId(123L);

        UserDao userDao3 = new UserDao();
        userDao3.setBookingDaoList(new ArrayList<>());
        userDao3.setId(123L);
        userDao3.setIsDeleted(true);
        userDao3.setPassword("iloveyou");
        userDao3.setProfile(profileDao2);

        ProfileDao profileDao3 = new ProfileDao();
        profileDao3.setFamilyDaoList(new ArrayList<>());
        profileDao3.setHealthFacilityDaoList(new ArrayList<>());
        profileDao3.setIsDeleted(true);
        profileDao3.setRole(AppConstant.ProfileRole.USER);
        profileDao3.setUser(userDao3);
        profileDao3.setUserId(123L);

        UserDao userDao4 = new UserDao();
        userDao4.setBookingDaoList(new ArrayList<>());
        userDao4.setId(123L);
        userDao4.setIsDeleted(true);
        userDao4.setPassword("iloveyou");
        userDao4.setProfile(profileDao3);

        when(userRepository.save((UserDao) any())).thenReturn(userDao4);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        UserDto userDto = new UserDto();
        ProfileDto profileDto = new ProfileDto();
        userDto.setProfile(profileDto);

        ResponseEntity<Object> actualRegisterResult = authenticationService.register(userDto);

        assertEquals(HttpStatus.OK, actualRegisterResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualRegisterResult.getBody()).getData();

        ProfileDto profile = ((UserDto) data).getProfile();

        assertEquals(profileDto, profile);
        assertEquals("secret", ((UserDto) data).getPassword());

        ApiResponseStatus status = ((ApiResponse<Object>) actualRegisterResult.getBody()).getStatus();

        assertNull(((UserDto) data).getUsername());
        assertNull(((UserDto) data).getId());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertNull(profile.getUserId());
        assertNull(profile.getRole());

        verify(userRepository).save((UserDao) any());
        verify(passwordEncoder).encode((CharSequence) any());
    }


    @Test
    void authenticateUserException_Test() throws AuthenticationException {
        when(jwtTokenProvider.generateJwtToken((Authentication) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenThrow(new RuntimeException("An error occurred"));

        assertThrows(RuntimeException.class,
                () -> authenticationService.authenticateUser(new UsernamePassword("dimas", "iloveyou")));

        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void authenticateUser_Test3() throws AuthenticationException {
        when(jwtTokenProvider.generateJwtToken((Authentication) any())).thenReturn("ABC123");

        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        when(authenticationManager.authenticate((Authentication) any())).thenReturn(new TestingAuthenticationToken(
                new UserDetailsDao(123L, "dimas", "email@email.com", "iloveyou", grantedAuthorityList), "Credentials"));

        ResponseEntity<Object> actualAuthenticateUserResult = authenticationService
                .authenticateUser(new UsernamePassword("dimas", "iloveyou"));

        assertEquals(HttpStatus.OK, actualAuthenticateUserResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualAuthenticateUserResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualAuthenticateUserResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals("ABC123", ((JwtResponse) data).getToken());
        assertEquals(grantedAuthorityList, ((JwtResponse) data).getRoles());

        verify(jwtTokenProvider).generateJwtToken((Authentication) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }
}

