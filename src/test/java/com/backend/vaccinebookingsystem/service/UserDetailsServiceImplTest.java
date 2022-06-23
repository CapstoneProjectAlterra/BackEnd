package com.backend.vaccinebookingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.BookingDao;
import com.backend.vaccinebookingsystem.domain.dao.ProfileDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDetailsDao;
import com.backend.vaccinebookingsystem.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    void loadUserByUsername_Test() throws UsernameNotFoundException {
        ProfileDao profileDao = new ProfileDao();
        profileDao.setFamilyDaoList(new ArrayList<>());
        profileDao.setHealthFacilityDaoList(new ArrayList<>());
        profileDao.setRole(AppConstant.ProfileRole.USER);
        profileDao.setUser(new UserDao());
        profileDao.setUserId(123L);

        UserDao userDao = new UserDao();
        userDao.setBookingDaoList(new ArrayList<>());
        userDao.setId(123L);
        userDao.setPassword("iloveyou");
        userDao.setProfile(profileDao);
        userDao.setUsername("dimas");

        ProfileDao profileDao1 = new ProfileDao();
        profileDao1.setFamilyDaoList(new ArrayList<>());
        profileDao1.setHealthFacilityDaoList(new ArrayList<>());
        profileDao1.setRole(AppConstant.ProfileRole.USER);
        profileDao1.setUser(userDao);
        profileDao1.setUserId(123L);

        UserDao userDao1 = new UserDao();
        userDao1.setBookingDaoList(new ArrayList<>());
        userDao1.setId(123L);
        userDao1.setPassword("iloveyou");
        userDao1.setProfile(profileDao1);
        userDao1.setUsername("dimas");

        Optional<UserDao> ofResult = Optional.of(userDao1);
        when(userRepository.findByUsername((String) any())).thenReturn(ofResult);

        UserDetails actualLoadUserByUsernameResult = userDetailsServiceImpl.loadUserByUsername("janedoe");
        Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals(123L, ((UserDetailsDao) actualLoadUserByUsernameResult).getId().longValue());
        assertEquals("dimas", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals("USER", ((List<? extends GrantedAuthority>) authorities).get(0).getAuthority());

        verify(userRepository).findByUsername((String) any());
    }
}

