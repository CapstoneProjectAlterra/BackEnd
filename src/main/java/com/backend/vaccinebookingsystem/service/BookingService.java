package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.BookingDao;
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dao.UserDao;
import com.backend.vaccinebookingsystem.domain.dto.*;
import com.backend.vaccinebookingsystem.repository.BookingRepository;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;
import com.backend.vaccinebookingsystem.repository.UserRepository;
import com.backend.vaccinebookingsystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> createBooking(BookingDto bookingDto) {
        try {
            log.info("Creating new Booking. Value {}", bookingDto);

            Optional<BookingDao> optionalBookingDaoScheduleId = bookingRepository.findFirstByScheduleIdOrderByIdDesc(
                    bookingDto.getSchedule().getId());

            Optional<ScheduleDao> optionalScheduleDao = scheduleRepository.findById(bookingDto.getSchedule().getId());

            Optional<UserDao> optionalUserDao = userRepository.findById(bookingDto.getUser().getId());

            if (optionalScheduleDao.isEmpty() || optionalUserDao.isEmpty()) {
                log.info("Booking not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            if (optionalBookingDaoScheduleId.isEmpty()) {
                log.info("Find first empty");
                BookingDao bookingDao = BookingDao.builder()
                        .id(bookingDto.getId())
                        .bookingPass(1)
                        .bookingDate(bookingDto.getBookingDate())
                        .user(optionalUserDao.get())
                        .schedule(optionalScheduleDao.get())
                        .build();

                bookingRepository.save(bookingDao);

                BookingDto dto = BookingDto.builder()
                        .id(bookingDao.getId())
                        .bookingPass(bookingDao.getBookingPass())
                        .bookingDate(bookingDao.getBookingDate())
                        .user(UserDto.builder()
                                .id(bookingDao.getUser().getId())
                                .username(bookingDao.getUser().getUsername())
                                .name(bookingDao.getUser().getName())
                                .email(bookingDao.getUser().getEmail())
                                .password(bookingDao.getUser().getPassword())
                                .profile(ProfileDto.builder()
                                        .userId(optionalUserDao.get().getProfile().getUserId())
                                        .role(optionalUserDao.get().getProfile().getRole())
                                        .build())
                                .build())
                        .schedule(ScheduleDto.builder()
                                .id(bookingDao.getSchedule().getId())
                                .vaccinationDate(bookingDao.getSchedule().getVaccinationDate())
                                .operationalHourStart(bookingDao.getSchedule().getOperationalHourStart())
                                .operationalHourEnd(bookingDao.getSchedule().getOperationalHourEnd())
                                .quota(bookingDao.getSchedule().getQuota())
                                .dose(bookingDao.getSchedule().getDose())
                                .facility(HealthFacilityDto.builder()
                                        .id(optionalScheduleDao.get().getFacility().getId())
                                        .facilityName(optionalScheduleDao.get().getFacility().getFacilityName())
                                        .streetName(optionalScheduleDao.get().getFacility().getStreetName())
                                        .officeNumber(optionalScheduleDao.get().getFacility().getOfficeNumber())
                                        .villageName(optionalScheduleDao.get().getFacility().getVillageName())
                                        .district(optionalScheduleDao.get().getFacility().getDistrict())
                                        .city(optionalScheduleDao.get().getFacility().getCity())
                                        .province(optionalScheduleDao.get().getFacility().getProvince())
                                        .postalCode(optionalScheduleDao.get().getFacility().getPostalCode())
                                        .profile(ProfileDto.builder()
                                                .userId(optionalScheduleDao.get().getFacility().getProfile().getUserId())
                                                .role(optionalScheduleDao.get().getFacility().getProfile().getRole())
                                                .build())
                                        .build())
                                .vaccine(VaccineTypeDto.builder()
                                        .id(optionalScheduleDao.get().getVaccine().getId())
                                        .vaccineName(optionalScheduleDao.get().getVaccine().getVaccineName())
                                        .build())
                                .build())
                        .build();

                return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
            }

            log.info("Find first present");
            BookingDao bookingDao = BookingDao.builder()
                    .id(bookingDto.getId())
                    .bookingPass(optionalBookingDaoScheduleId.get().getBookingPass() + 1)
                    .bookingDate(bookingDto.getBookingDate())
                    .user(optionalUserDao.get())
                    .schedule(optionalScheduleDao.get())
                    .build();

            bookingRepository.save(bookingDao);

            BookingDto dto = BookingDto.builder()
                    .id(bookingDao.getId())
                    .bookingPass(bookingDao.getBookingPass())
                    .bookingDate(bookingDao.getBookingDate())
                    .user(UserDto.builder()
                            .id(bookingDao.getUser().getId())
                            .username(bookingDao.getUser().getUsername())
                            .name(bookingDao.getUser().getName())
                            .email(bookingDao.getUser().getEmail())
                            .password(bookingDao.getUser().getPassword())
                            .profile(ProfileDto.builder()
                                    .userId(optionalUserDao.get().getProfile().getUserId())
                                    .role(optionalUserDao.get().getProfile().getRole())
                                    .build())
                            .build())
                    .schedule(ScheduleDto.builder()
                            .id(bookingDao.getSchedule().getId())
                            .vaccinationDate(bookingDao.getSchedule().getVaccinationDate())
                            .operationalHourStart(bookingDao.getSchedule().getOperationalHourStart())
                            .operationalHourEnd(bookingDao.getSchedule().getOperationalHourEnd())
                            .quota(bookingDao.getSchedule().getQuota())
                            .dose(bookingDao.getSchedule().getDose())
                            .facility(HealthFacilityDto.builder()
                                    .id(optionalScheduleDao.get().getFacility().getId())
                                    .facilityName(optionalScheduleDao.get().getFacility().getFacilityName())
                                    .streetName(optionalScheduleDao.get().getFacility().getStreetName())
                                    .officeNumber(optionalScheduleDao.get().getFacility().getOfficeNumber())
                                    .villageName(optionalScheduleDao.get().getFacility().getVillageName())
                                    .district(optionalScheduleDao.get().getFacility().getDistrict())
                                    .city(optionalScheduleDao.get().getFacility().getCity())
                                    .province(optionalScheduleDao.get().getFacility().getProvince())
                                    .postalCode(optionalScheduleDao.get().getFacility().getPostalCode())
                                    .profile(ProfileDto.builder()
                                            .userId(optionalScheduleDao.get().getFacility().getProfile().getUserId())
                                            .role(optionalScheduleDao.get().getFacility().getProfile().getRole())
                                            .build())
                                    .build())
                            .vaccine(VaccineTypeDto.builder()
                                    .id(optionalScheduleDao.get().getVaccine().getId())
                                    .vaccineName(optionalScheduleDao.get().getVaccine().getVaccineName())
                                    .build())
                            .build())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Booking. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getBookingById(Long id) {
        try {
            log.info("Getting a Booking by id");
            Optional<BookingDao> optionalBookingDao = bookingRepository.findById(id);

            Optional<ScheduleDao> optionalScheduleDao = scheduleRepository.findById(optionalBookingDao.get().getSchedule().getId());

            Optional<UserDao> optionalUserDao = userRepository.findById(optionalBookingDao.get().getUser().getId());

            if (optionalBookingDao.isEmpty() || optionalScheduleDao.isEmpty() || optionalUserDao.isEmpty()) {
                log.info("Booking not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking found");
            BookingDto dto = BookingDto.builder()
                    .id(optionalBookingDao.get().getId())
                    .bookingPass(optionalBookingDao.get().getBookingPass())
                    .bookingDate(optionalBookingDao.get().getBookingDate())
                    .user(UserDto.builder()
                            .id(optionalBookingDao.get().getUser().getId())
                            .username(optionalBookingDao.get().getUser().getUsername())
                            .name(optionalBookingDao.get().getUser().getName())
                            .email(optionalBookingDao.get().getUser().getEmail())
                            .password(optionalBookingDao.get().getUser().getPassword())
                            .profile(ProfileDto.builder()
                                    .userId(optionalUserDao.get().getProfile().getUserId())
                                    .role(optionalUserDao.get().getProfile().getRole())
                                    .build())
                            .build())
                    .schedule(ScheduleDto.builder()
                            .id(optionalBookingDao.get().getSchedule().getId())
                            .vaccinationDate(optionalBookingDao.get().getSchedule().getVaccinationDate())
                            .operationalHourStart(optionalBookingDao.get().getSchedule().getOperationalHourStart())
                            .operationalHourEnd(optionalBookingDao.get().getSchedule().getOperationalHourEnd())
                            .quota(optionalBookingDao.get().getSchedule().getQuota())
                            .dose(optionalBookingDao.get().getSchedule().getDose())
                            .facility(HealthFacilityDto.builder()
                                    .id(optionalScheduleDao.get().getFacility().getId())
                                    .facilityName(optionalScheduleDao.get().getFacility().getFacilityName())
                                    .streetName(optionalScheduleDao.get().getFacility().getStreetName())
                                    .officeNumber(optionalScheduleDao.get().getFacility().getOfficeNumber())
                                    .villageName(optionalScheduleDao.get().getFacility().getVillageName())
                                    .district(optionalScheduleDao.get().getFacility().getDistrict())
                                    .city(optionalScheduleDao.get().getFacility().getCity())
                                    .province(optionalScheduleDao.get().getFacility().getProvince())
                                    .postalCode(optionalScheduleDao.get().getFacility().getPostalCode())
                                    .profile(ProfileDto.builder()
                                            .userId(optionalScheduleDao.get().getFacility().getProfile().getUserId())
                                            .role(optionalScheduleDao.get().getFacility().getProfile().getRole())
                                            .build())
                                    .build())
                            .vaccine(VaccineTypeDto.builder()
                                    .id(optionalScheduleDao.get().getVaccine().getId())
                                    .vaccineName(optionalScheduleDao.get().getVaccine().getVaccineName())
                                    .build())
                            .build())
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a Booking by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllBookings() {
        try {
            log.info("Getting all of Bookings");
            List<BookingDao> bookingDaoList;
            List<BookingDto> bookingDtos = new ArrayList<>();

            bookingDaoList = bookingRepository.findAll();

            for (BookingDao bookingDao : bookingDaoList) {
                Optional<ScheduleDao> optionalScheduleDao = scheduleRepository.findById(bookingDao.getSchedule().getId());

                Optional<UserDao> optionalUserDao = userRepository.findById(bookingDao.getUser().getId());

                bookingDtos.add(BookingDto.builder()
                        .id(bookingDao.getId())
                        .bookingPass(bookingDao.getBookingPass())
                        .bookingDate(bookingDao.getBookingDate())
                        .user(UserDto.builder()
                                .id(bookingDao.getUser().getId())
                                .username(bookingDao.getUser().getUsername())
                                .name(bookingDao.getUser().getName())
                                .email(bookingDao.getUser().getEmail())
                                .password(bookingDao.getUser().getPassword())
                                .profile(ProfileDto.builder()
                                        .userId(optionalUserDao.get().getProfile().getUserId())
                                        .role(optionalUserDao.get().getProfile().getRole())
                                        .build())
                                .build())
                        .schedule(ScheduleDto.builder()
                                .id(bookingDao.getSchedule().getId())
                                .vaccinationDate(bookingDao.getSchedule().getVaccinationDate())
                                .operationalHourStart(bookingDao.getSchedule().getOperationalHourStart())
                                .operationalHourEnd(bookingDao.getSchedule().getOperationalHourEnd())
                                .quota(bookingDao.getSchedule().getQuota())
                                .dose(bookingDao.getSchedule().getDose())
                                .facility(HealthFacilityDto.builder()
                                        .id(optionalScheduleDao.get().getFacility().getId())
                                        .facilityName(optionalScheduleDao.get().getFacility().getFacilityName())
                                        .streetName(optionalScheduleDao.get().getFacility().getStreetName())
                                        .officeNumber(optionalScheduleDao.get().getFacility().getOfficeNumber())
                                        .villageName(optionalScheduleDao.get().getFacility().getVillageName())
                                        .district(optionalScheduleDao.get().getFacility().getDistrict())
                                        .city(optionalScheduleDao.get().getFacility().getCity())
                                        .province(optionalScheduleDao.get().getFacility().getProvince())
                                        .postalCode(optionalScheduleDao.get().getFacility().getPostalCode())
                                        .profile(ProfileDto.builder()
                                                .userId(optionalScheduleDao.get().getFacility().getProfile().getUserId())
                                                .role(optionalScheduleDao.get().getFacility().getProfile().getRole())
                                                .build())
                                        .build())
                                .vaccine(VaccineTypeDto.builder()
                                        .id(optionalScheduleDao.get().getVaccine().getId())
                                        .vaccineName(optionalScheduleDao.get().getVaccine().getVaccineName())
                                        .build())
                                .build())
                        .build());
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, bookingDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Bookings. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateBookingById(Long id, BookingDto bookingDto) {
        try {
            log.info("Updating a Booking by id");
            Optional<BookingDao> optionalBookingDao = bookingRepository.findById(id);

            Optional<ScheduleDao> optionalScheduleDao = scheduleRepository.findById(bookingDto.getSchedule().getId());

            Optional<UserDao> optionalUserDao = userRepository.findById(bookingDto.getUser().getId());

            if (optionalBookingDao.isEmpty() || optionalScheduleDao.isEmpty() || optionalUserDao.isEmpty()) {
                log.info("Booking not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking found");
            BookingDao bookingDao = optionalBookingDao.get();
            bookingDao.setBookingPass(bookingDto.getBookingPass());
            bookingDao.setBookingDate(bookingDto.getBookingDate());
            bookingDao.setUser(optionalUserDao.get());
            bookingDao.setSchedule(optionalScheduleDao.get());
            bookingRepository.save(bookingDao);

            BookingDto dto = BookingDto.builder()
                    .id(bookingDao.getId())
                    .bookingPass(bookingDao.getBookingPass())
                    .bookingDate(bookingDao.getBookingDate())
                    .user(UserDto.builder()
                            .id(bookingDao.getUser().getId())
                            .username(bookingDao.getUser().getUsername())
                            .name(bookingDao.getUser().getName())
                            .email(bookingDao.getUser().getEmail())
                            .password(bookingDao.getUser().getPassword())
                            .profile(ProfileDto.builder()
                                    .userId(optionalUserDao.get().getProfile().getUserId())
                                    .role(optionalUserDao.get().getProfile().getRole())
                                    .build())
                            .build())
                    .schedule(ScheduleDto.builder()
                            .id(bookingDao.getSchedule().getId())
                            .vaccinationDate(bookingDao.getSchedule().getVaccinationDate())
                            .operationalHourStart(bookingDao.getSchedule().getOperationalHourStart())
                            .operationalHourEnd(bookingDao.getSchedule().getOperationalHourEnd())
                            .quota(bookingDao.getSchedule().getQuota())
                            .dose(bookingDao.getSchedule().getDose())
                            .facility(HealthFacilityDto.builder()
                                    .id(optionalScheduleDao.get().getFacility().getId())
                                    .facilityName(optionalScheduleDao.get().getFacility().getFacilityName())
                                    .streetName(optionalScheduleDao.get().getFacility().getStreetName())
                                    .officeNumber(optionalScheduleDao.get().getFacility().getOfficeNumber())
                                    .villageName(optionalScheduleDao.get().getFacility().getVillageName())
                                    .district(optionalScheduleDao.get().getFacility().getDistrict())
                                    .city(optionalScheduleDao.get().getFacility().getCity())
                                    .province(optionalScheduleDao.get().getFacility().getProvince())
                                    .postalCode(optionalScheduleDao.get().getFacility().getPostalCode())
                                    .profile(ProfileDto.builder()
                                            .userId(optionalScheduleDao.get().getFacility().getProfile().getUserId())
                                            .role(optionalScheduleDao.get().getFacility().getProfile().getRole())
                                            .build())
                                    .build())
                            .vaccine(VaccineTypeDto.builder()
                                    .id(optionalScheduleDao.get().getVaccine().getId())
                                    .vaccineName(optionalScheduleDao.get().getVaccine().getVaccineName())
                                    .build())
                            .build())
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Booking by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteBookingById(Long id) {
        try {
            log.info("Deleting a Booking by id");
            Optional<BookingDao> optionalBookingDao = bookingRepository.findById(id);
            if (optionalBookingDao.isEmpty()) {
                log.info("Booking not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking found");
            bookingRepository.delete(optionalBookingDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Booking by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> countBookingByScheduleId(Long scheduleId) {
        try {
            log.info("Counting registrant");

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, bookingRepository.countBookingByScheduleId(scheduleId), HttpStatus.OK);
        } catch (Exception e) {
            log.info("An error occurred in Counting registrant. Error {}", e.getMessage());
            throw e;
        }
    }
}
