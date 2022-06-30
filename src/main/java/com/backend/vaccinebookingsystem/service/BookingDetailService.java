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
            Optional<BookingDetailDao> optionalBookingDetailDaoBooking = bookingDetailRepository.findTopByBookingId(bookingId);
            Optional<BookingDetailDao> optionalBookingDetailDaoFamily = bookingDetailRepository.findTopByFamilyId(familyId);

            if (optionalBookingDetailDaoBooking.isEmpty() || optionalBookingDetailDaoFamily.isEmpty()) {
                log.info("Booking Detail not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking Detail found");
            BookingDetailDto detailDto = BookingDetailDto.builder()
                    .bookingId(optionalBookingDetailDaoBooking.get().getBookingId())
                    .familyId(optionalBookingDetailDaoFamily.get().getFamilyId())
                    .bookingStatus(optionalBookingDetailDaoFamily.get().getBookingStatus())
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
                bookingDetailDtos.add(BookingDetailDto.builder()
                        .bookingId(bookingDetailDao.getBookingId())
                        .familyId(bookingDetailDao.getFamilyId())
                        .bookingStatus(bookingDetailDao.getBookingStatus())
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
            Optional<BookingDetailDao> optionalBookingDetailDaoBooking = bookingDetailRepository.findTopByBookingId(bookingId);
            Optional<BookingDetailDao> optionalBookingDetailDaoFamily = bookingDetailRepository.findTopByFamilyId(familyId);

            if (optionalBookingDetailDaoBooking.isEmpty() || optionalBookingDetailDaoFamily.isEmpty()) {
                log.info("Booking Detail not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking Detail found");
            BookingDetailDao bookingDetailDao = optionalBookingDetailDaoBooking.get();
            bookingDetailDao.setBookingStatus(bookingDetailDto.getBookingStatus());
            bookingDetailRepository.save(bookingDetailDao);

            BookingDetailDto detailDto = BookingDetailDto.builder()
                    .bookingId(bookingDetailDao.getBookingId())
                    .familyId(bookingDetailDao.getFamilyId())
                    .bookingStatus(bookingDetailDao.getBookingStatus())
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
            Optional<BookingDetailDao> optionalBookingDetailDaoBooking = bookingDetailRepository.findTopByBookingId(bookingId);
            Optional<BookingDetailDao> optionalBookingDetailDaoFamily = bookingDetailRepository.findTopByFamilyId(familyId);

            if (optionalBookingDetailDaoBooking.isEmpty() || optionalBookingDetailDaoFamily.isEmpty()) {
                log.info("Booking Detail not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Stock found");
            bookingDetailRepository.delete(optionalBookingDetailDaoBooking.get());
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in deleting Stock by id. Error {}", e.getMessage());
            throw e;
        }
    }
}
