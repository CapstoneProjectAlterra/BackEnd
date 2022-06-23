package com.backend.vaccinebookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.common.ApiResponseStatus;
import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDetailsDao;
import com.backend.vaccinebookingsystem.domain.dto.JwtResponse;
import com.backend.vaccinebookingsystem.domain.dto.JwtTokenProvider;
import com.backend.vaccinebookingsystem.domain.dto.ProfileDto;
import com.backend.vaccinebookingsystem.domain.dto.UserDto;
import com.backend.vaccinebookingsystem.domain.dto.UsernamePassword;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Disabled;
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

@ContextConfiguration(classes = {AuthenticationService.class})
@ExtendWith(SpringExtension.class)
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
        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setPassword("iloveyou");
        userDao.setProfile(new ProfileDao());
        userDao.setUpdatedAt(null);

        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(userDao);
        profileDao.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao);

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao1);
        profileDao1.setUserId(123L);

        FamilyDao familyDao = new FamilyDao();
        familyDao.setBookingDaoList(new ArrayList<>());
        familyDao.setGender(AppConstant.Gender.LAKI_LAKI);
        familyDao.setId(123L);
        familyDao.setNIK("NIK");
        familyDao.setProfile(profileDao1);

        when(familyRepository.save((FamilyDao) any())).thenReturn(familyDao);

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

        verify(familyRepository).save((FamilyDao) any());
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
                new UserDetailsDao(123L, "dimas", "full", "email@email.com", "iloveyou", grantedAuthorityList), "Credentials"));

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

