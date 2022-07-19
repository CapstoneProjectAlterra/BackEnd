package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDetailsDao;
import com.backend.vaccinebookingsystem.domain.dto.JwtResponse;
import com.backend.vaccinebookingsystem.domain.dto.JwtTokenProvider;
import com.backend.vaccinebookingsystem.domain.dto.UserDto;
import com.backend.vaccinebookingsystem.domain.dto.UsernamePassword;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
import com.backend.vaccinebookingsystem.repository.ProfileRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AuthenticationService.class)
class AuthenticationServiceTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private FamilyRepository familyRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void registerAlreadyExists_Test() {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setName("Name");
        userDao.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setName("Name");
        userDao1.setProfile(profileDao1);
        
        Optional<UserDao> ofResult = Optional.of(userDao1);
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);
        
        ResponseEntity<Object> actualRegisterResult = authenticationService.register(new UserDto());
        
        assertEquals(HttpStatus.CONFLICT, actualRegisterResult.getStatusCode());
        
        ApiResponseStatus status = ((ApiResponse<Object>) actualRegisterResult.getBody()).getStatus();
        
        assertEquals("ALREADY_EXISTS", status.getCode());

        verify(userRepository).findByUsername((String) any());
    }

    @Test
    void authenticateUser_Test() throws AuthenticationException {
        when(jwtTokenProvider.generateJwtToken((Authentication) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        ResponseEntity<Object> actualAuthenticateUserResult = authenticationService
                .authenticateUser(new UsernamePassword("dimas", "iloveyou"));

        assertEquals(HttpStatus.BAD_REQUEST, actualAuthenticateUserResult.getStatusCode());

        ApiResponseStatus status = ((ApiResponse<Object>) actualAuthenticateUserResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), status.getCode());

        verify(jwtTokenProvider).generateJwtToken((Authentication) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void authenticateUserException_Test() throws AuthenticationException {
        when(jwtTokenProvider.generateJwtToken((Authentication) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken(new UserDetailsDao(), "Credentials"));
        ResponseEntity<Object> actualAuthenticateUserResult = authenticationService
                .authenticateUser(new UsernamePassword("dimas", "iloveyou"));
        assertTrue(actualAuthenticateUserResult.hasBody());
        assertTrue(actualAuthenticateUserResult.getHeaders().isEmpty());
        assertEquals(HttpStatus.BAD_REQUEST, actualAuthenticateUserResult.getStatusCode());
        assertNull(((ApiResponse<Object>) actualAuthenticateUserResult.getBody()).getData());
        ApiResponseStatus status = ((ApiResponse<Object>) actualAuthenticateUserResult.getBody()).getStatus();
        
        assertEquals(AppConstant.ResponseCode.DATA_NOT_FOUND.getCode(), status.getCode());
        
        verify(jwtTokenProvider).generateJwtToken((Authentication) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }

    @Test
    void authenticateUserSuccess_Test() throws AuthenticationException {
        when(jwtTokenProvider.generateJwtToken((Authentication) any())).thenReturn("ABC123");

        ArrayList<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken(new UserDetailsDao(123L, "dimas", "Generating JWT token",
                        "jane.doe@example.org", "iloveyou", grantedAuthorityList), "Credentials"));
        ResponseEntity<Object> actualAuthenticateUserResult = authenticationService
                .authenticateUser(new UsernamePassword("dimas", "iloveyou"));

        assertEquals(HttpStatus.OK, actualAuthenticateUserResult.getStatusCode());

        Object data = ((ApiResponse<Object>) actualAuthenticateUserResult.getBody()).getData();

        ApiResponseStatus status = ((ApiResponse<Object>) actualAuthenticateUserResult.getBody()).getStatus();

        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), status.getCode());
        assertEquals(123L, ((JwtResponse) data).getUserId().longValue());
        assertEquals("ABC123", ((JwtResponse) data).getToken());
        assertEquals("dimas", ((JwtResponse) data).getUsername());
        assertEquals(grantedAuthorityList, ((JwtResponse) data).getRoles());

        verify(jwtTokenProvider).generateJwtToken((Authentication) any());
        verify(authenticationManager).authenticate((Authentication) any());
    }
}

