package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.BookingDao;
import com.backend.vaccinebookingsystem.domain.dao.BookingDetailDao;
import com.backend.vaccinebookingsystem.domain.dao.FamilyDao;
import com.backend.vaccinebookingsystem.domain.dto.*;
import com.backend.vaccinebookingsystem.repository.BookingDetailRepository;
import com.backend.vaccinebookingsystem.repository.BookingRepository;
import com.backend.vaccinebookingsystem.repository.FamilyRepository;
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
public class BookingDetailService {

    @Autowired
    private BookingDetailRepository bookingDetailRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FamilyRepository familyRepository;

    public ResponseEntity<Object> createBookingDetail(BookingDetailDto bookingDetailDto) {
        try {
            log.info("Creating new Booking Detail");
            Optional<BookingDao> optionalBookingDao = bookingRepository.findById(bookingDetailDto.getBookingId());

            Optional<FamilyDao> optionalFamilyDao = familyRepository.findById(bookingDetailDto.getFamilyId());

            if (optionalBookingDao.isEmpty() || optionalFamilyDao.isEmpty()) {
                log.info("Booking Detail not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking Detail found");
            BookingDetailDao bookingDetailDao = BookingDetailDao.builder()
                    .bookingId(bookingDetailDto.getBookingId())
                    .familyId(bookingDetailDto.getFamilyId())
                    .bookingStatus(bookingDetailDto.getBookingStatus())
                    .build();

            bookingDetailRepository.save(bookingDetailDao);

            BookingDetailDto detailDto = BookingDetailDto.builder()
                    .bookingId(bookingDetailDao.getBookingId())
                    .familyId(bookingDetailDao.getFamilyId())
                    .bookingStatus(bookingDetailDao.getBookingStatus())
                    .family(FamilyDto.builder()
                            .id(optionalFamilyDao.get().getId())
                            .statusInFamily(optionalFamilyDao.get().getStatusInFamily())
                            .NIK(optionalFamilyDao.get().getNIK())
                            .name(optionalFamilyDao.get().getName())
                            .email(optionalFamilyDao.get().getEmail())
                            .phoneNumber(optionalFamilyDao.get().getPhoneNumber())
                            .gender(optionalFamilyDao.get().getGender())
                            .placeOfBirth(optionalFamilyDao.get().getPlaceOfBirth())
                            .dateOfBirth(optionalFamilyDao.get().getDateOfBirth())
                            .residenceAddress(optionalFamilyDao.get().getResidenceAddress())
                            .idCardAddress(optionalFamilyDao.get().getIdCardAddress())
                            .build())
                    .booking(BookingDto.builder()
                            .id(optionalBookingDao.get().getId())
                            .bookingPass(optionalBookingDao.get().getBookingPass())
                            .bookingDate(optionalBookingDao.get().getBookingDate())
                            .user(UserDto.builder()
                                    .id(optionalBookingDao.get().getUser().getId())
                                    .username(optionalBookingDao.get().getUser().getUsername())
                                    .name(optionalBookingDao.get().getUser().getName())
                                    .email(optionalBookingDao.get().getUser().getEmail())
                                    .build())
                            .schedule(ScheduleDto.builder()
                                    .id(optionalBookingDao.get().getSchedule().getId())
                                    .vaccinationDate(optionalBookingDao.get().getSchedule().getVaccinationDate())
                                    .operationalHourStart(optionalBookingDao.get().getSchedule().getOperationalHourStart())
                                    .operationalHourEnd(optionalBookingDao.get().getSchedule().getOperationalHourEnd())
                                    .quota(optionalBookingDao.get().getSchedule().getQuota())
                                    .dose(optionalBookingDao.get().getSchedule().getDose())
                                    .facility(HealthFacilityDto.builder()
                                            .id(optionalBookingDao.get().getSchedule().getFacility().getId())
                                            .facilityName(optionalBookingDao.get().getSchedule().getFacility().getFacilityName())
                                            .streetName(optionalBookingDao.get().getSchedule().getFacility().getStreetName())
                                            .officeNumber(optionalBookingDao.get().getSchedule().getFacility().getOfficeNumber())
                                            .villageName(optionalBookingDao.get().getSchedule().getFacility().getVillageName())
                                            .district(optionalBookingDao.get().getSchedule().getFacility().getDistrict())
                                            .city(optionalBookingDao.get().getSchedule().getFacility().getCity())
                                            .province(optionalBookingDao.get().getSchedule().getFacility().getProvince())
                                            .postalCode(optionalBookingDao.get().getSchedule().getFacility().getPostalCode())
                                            .build())
                                    .vaccine(VaccineTypeDto.builder()
                                            .id(optionalBookingDao.get().getSchedule().getVaccine().getId())
                                            .vaccineName(optionalBookingDao.get().getSchedule().getVaccine().getVaccineName())
                                            .build())
                                    .build())
                            .build())
                    .build();

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, detailDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating new Booking Detail. Error: {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> searchBookingDetailById(Long bookingId, Long familyId) {
        try {
            log.info("Getting a Booking Detail by id");
            Optional<BookingDetailDao> optionalBookingDetailDao = bookingDetailRepository.findByBookingIdAndFamilyId(bookingId, familyId);

            if (optionalBookingDetailDao.isEmpty()) {
                log.info("Booking Detail not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking Detail found");
            BookingDetailDto detailDto = BookingDetailDto.builder()
                    .bookingId(optionalBookingDetailDao.get().getBookingId())
                    .familyId(optionalBookingDetailDao.get().getFamilyId())
                    .bookingStatus(optionalBookingDetailDao.get().getBookingStatus())
                    .family(FamilyDto.builder()
                            .id(optionalBookingDetailDao.get().getFamily().getId())
                            .statusInFamily(optionalBookingDetailDao.get().getFamily().getStatusInFamily())
                            .NIK(optionalBookingDetailDao.get().getFamily().getNIK())
                            .name(optionalBookingDetailDao.get().getFamily().getName())
                            .email(optionalBookingDetailDao.get().getFamily().getEmail())
                            .phoneNumber(optionalBookingDetailDao.get().getFamily().getPhoneNumber())
                            .gender(optionalBookingDetailDao.get().getFamily().getGender())
                            .placeOfBirth(optionalBookingDetailDao.get().getFamily().getPlaceOfBirth())
                            .dateOfBirth(optionalBookingDetailDao.get().getFamily().getDateOfBirth())
                            .residenceAddress(optionalBookingDetailDao.get().getFamily().getResidenceAddress())
                            .idCardAddress(optionalBookingDetailDao.get().getFamily().getIdCardAddress())
                            .build())
                    .booking(BookingDto.builder()
                            .id(optionalBookingDetailDao.get().getBooking().getId())
                            .bookingPass(optionalBookingDetailDao.get().getBooking().getBookingPass())
                            .bookingDate(optionalBookingDetailDao.get().getBooking().getBookingDate())
                            .user(UserDto.builder()
                                    .id(optionalBookingDetailDao.get().getBooking().getUser().getId())
                                    .username(optionalBookingDetailDao.get().getBooking().getUser().getUsername())
                                    .name(optionalBookingDetailDao.get().getBooking().getUser().getName())
                                    .email(optionalBookingDetailDao.get().getBooking().getUser().getEmail())
                                    .build())
                            .schedule(ScheduleDto.builder()
                                    .id(optionalBookingDetailDao.get().getBooking().getSchedule().getId())
                                    .vaccinationDate(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccinationDate())
                                    .operationalHourStart(optionalBookingDetailDao.get().getBooking().getSchedule().getOperationalHourStart())
                                    .operationalHourEnd(optionalBookingDetailDao.get().getBooking().getSchedule().getOperationalHourEnd())
                                    .quota(optionalBookingDetailDao.get().getBooking().getSchedule().getQuota())
                                    .dose(optionalBookingDetailDao.get().getBooking().getSchedule().getDose())
                                    .facility(HealthFacilityDto.builder()
                                            .id(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getId())
                                            .facilityName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getFacilityName())
                                            .streetName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getStreetName())
                                            .officeNumber(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getOfficeNumber())
                                            .villageName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getVillageName())
                                            .district(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getDistrict())
                                            .city(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getCity())
                                            .province(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getProvince())
                                            .postalCode(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getPostalCode())
                                            .build())
                                    .vaccine(VaccineTypeDto.builder()
                                            .id(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccine().getId())
                                            .vaccineName(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccine().getVaccineName())
                                            .build())
                                    .build())
                            .build())
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, detailDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting a Booking Detail by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> getAllBookingDetails() {
        try {
            log.info("Getting all of Booking Details");
            List<BookingDetailDao> bookingDetailDaoList;
            List<BookingDetailDto> bookingDetailDtos = new ArrayList<>();

            bookingDetailDaoList = bookingDetailRepository.findAll();

            for (BookingDetailDao bookingDetailDao : bookingDetailDaoList) {
                Optional<BookingDetailDao> optionalBookingDetailDao = bookingDetailRepository.findByBookingIdAndFamilyId(bookingDetailDao.getBookingId(), bookingDetailDao.getFamilyId());

                bookingDetailDtos.add(BookingDetailDto.builder()
                        .bookingId(bookingDetailDao.getBookingId())
                        .familyId(bookingDetailDao.getFamilyId())
                        .bookingStatus(bookingDetailDao.getBookingStatus())
                        .family(FamilyDto.builder()
                                .id(optionalBookingDetailDao.get().getFamily().getId())
                                .statusInFamily(optionalBookingDetailDao.get().getFamily().getStatusInFamily())
                                .NIK(optionalBookingDetailDao.get().getFamily().getNIK())
                                .name(optionalBookingDetailDao.get().getFamily().getName())
                                .email(optionalBookingDetailDao.get().getFamily().getEmail())
                                .phoneNumber(optionalBookingDetailDao.get().getFamily().getPhoneNumber())
                                .gender(optionalBookingDetailDao.get().getFamily().getGender())
                                .placeOfBirth(optionalBookingDetailDao.get().getFamily().getPlaceOfBirth())
                                .dateOfBirth(optionalBookingDetailDao.get().getFamily().getDateOfBirth())
                                .residenceAddress(optionalBookingDetailDao.get().getFamily().getResidenceAddress())
                                .idCardAddress(optionalBookingDetailDao.get().getFamily().getIdCardAddress())
                                .build())
                        .booking(BookingDto.builder()
                                .id(optionalBookingDetailDao.get().getBooking().getId())
                                .bookingPass(optionalBookingDetailDao.get().getBooking().getBookingPass())
                                .bookingDate(optionalBookingDetailDao.get().getBooking().getBookingDate())
                                .user(UserDto.builder()
                                        .id(optionalBookingDetailDao.get().getBooking().getUser().getId())
                                        .username(optionalBookingDetailDao.get().getBooking().getUser().getUsername())
                                        .name(optionalBookingDetailDao.get().getBooking().getUser().getName())
                                        .email(optionalBookingDetailDao.get().getBooking().getUser().getEmail())
                                        .build())
                                .schedule(ScheduleDto.builder()
                                        .id(optionalBookingDetailDao.get().getBooking().getSchedule().getId())
                                        .vaccinationDate(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccinationDate())
                                        .operationalHourStart(optionalBookingDetailDao.get().getBooking().getSchedule().getOperationalHourStart())
                                        .operationalHourEnd(optionalBookingDetailDao.get().getBooking().getSchedule().getOperationalHourEnd())
                                        .quota(optionalBookingDetailDao.get().getBooking().getSchedule().getQuota())
                                        .dose(optionalBookingDetailDao.get().getBooking().getSchedule().getDose())
                                        .facility(HealthFacilityDto.builder()
                                                .id(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getId())
                                                .facilityName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getFacilityName())
                                                .streetName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getStreetName())
                                                .officeNumber(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getOfficeNumber())
                                                .villageName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getVillageName())
                                                .district(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getDistrict())
                                                .city(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getCity())
                                                .province(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getProvince())
                                                .postalCode(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getPostalCode())
                                                .build())
                                        .vaccine(VaccineTypeDto.builder()
                                                .id(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccine().getId())
                                                .vaccineName(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccine().getVaccineName())
                                                .build())
                                        .build())
                                .build())
                        .build());
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, bookingDetailDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting all of Booking Details. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateBookingDetailById(Long bookingId, Long familyId, BookingDetailDto bookingDetailDto) {
        try {
            log.info("Updating a Booking Detail by id");

            Optional<BookingDetailDao> optionalBookingDetailDao = bookingDetailRepository.findByBookingIdAndFamilyId(bookingId, familyId);

            if (optionalBookingDetailDao.isEmpty()) {
                log.info("Booking Detail not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking Detail found");
            BookingDetailDao bookingDetailDao = optionalBookingDetailDao.get();
            bookingDetailDao.setBookingStatus(bookingDetailDto.getBookingStatus());
            bookingDetailRepository.save(bookingDetailDao);

            BookingDetailDto detailDto = BookingDetailDto.builder()
                    .bookingId(bookingDetailDao.getBookingId())
                    .familyId(bookingDetailDao.getFamilyId())
                    .bookingStatus(bookingDetailDao.getBookingStatus())
                    .family(FamilyDto.builder()
                            .id(optionalBookingDetailDao.get().getFamily().getId())
                            .statusInFamily(optionalBookingDetailDao.get().getFamily().getStatusInFamily())
                            .NIK(optionalBookingDetailDao.get().getFamily().getNIK())
                            .name(optionalBookingDetailDao.get().getFamily().getName())
                            .email(optionalBookingDetailDao.get().getFamily().getEmail())
                            .phoneNumber(optionalBookingDetailDao.get().getFamily().getPhoneNumber())
                            .gender(optionalBookingDetailDao.get().getFamily().getGender())
                            .placeOfBirth(optionalBookingDetailDao.get().getFamily().getPlaceOfBirth())
                            .dateOfBirth(optionalBookingDetailDao.get().getFamily().getDateOfBirth())
                            .residenceAddress(optionalBookingDetailDao.get().getFamily().getResidenceAddress())
                            .idCardAddress(optionalBookingDetailDao.get().getFamily().getIdCardAddress())
                            .build())
                    .booking(BookingDto.builder()
                            .id(optionalBookingDetailDao.get().getBooking().getId())
                            .bookingPass(optionalBookingDetailDao.get().getBooking().getBookingPass())
                            .bookingDate(optionalBookingDetailDao.get().getBooking().getBookingDate())
                            .user(UserDto.builder()
                                    .id(optionalBookingDetailDao.get().getBooking().getUser().getId())
                                    .username(optionalBookingDetailDao.get().getBooking().getUser().getUsername())
                                    .name(optionalBookingDetailDao.get().getBooking().getUser().getName())
                                    .email(optionalBookingDetailDao.get().getBooking().getUser().getEmail())
                                    .build())
                            .schedule(ScheduleDto.builder()
                                    .id(optionalBookingDetailDao.get().getBooking().getSchedule().getId())
                                    .vaccinationDate(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccinationDate())
                                    .operationalHourStart(optionalBookingDetailDao.get().getBooking().getSchedule().getOperationalHourStart())
                                    .operationalHourEnd(optionalBookingDetailDao.get().getBooking().getSchedule().getOperationalHourEnd())
                                    .quota(optionalBookingDetailDao.get().getBooking().getSchedule().getQuota())
                                    .dose(optionalBookingDetailDao.get().getBooking().getSchedule().getDose())
                                    .facility(HealthFacilityDto.builder()
                                            .id(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getId())
                                            .facilityName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getFacilityName())
                                            .streetName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getStreetName())
                                            .officeNumber(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getOfficeNumber())
                                            .villageName(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getVillageName())
                                            .district(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getDistrict())
                                            .city(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getCity())
                                            .province(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getProvince())
                                            .postalCode(optionalBookingDetailDao.get().getBooking().getSchedule().getFacility().getPostalCode())
                                            .build())
                                    .vaccine(VaccineTypeDto.builder()
                                            .id(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccine().getId())
                                            .vaccineName(optionalBookingDetailDao.get().getBooking().getSchedule().getVaccine().getVaccineName())
                                            .build())
                                    .build())
                            .build())
                    .build();
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, detailDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in Updating Booking Detail by id. Error {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<Object> deleteBookingDetailById(Long bookingId, Long familyId) {
        try {
            log.info("Deleting a Booking Detail by id");
            Optional<BookingDetailDao> optionalBookingDetailDao = bookingDetailRepository.findByBookingIdAndFamilyId(bookingId, familyId);

            if (optionalBookingDetailDao.isEmpty()) {
                log.info("Booking Detail not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            bookingDetailRepository.delete(optionalBookingDetailDao.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
