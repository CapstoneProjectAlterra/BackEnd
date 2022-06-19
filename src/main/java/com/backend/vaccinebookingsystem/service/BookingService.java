package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.BookingDao;
import com.backend.vaccinebookingsystem.domain.dao.ScheduleDao;
import com.backend.vaccinebookingsystem.domain.dto.BookingDto;
import com.backend.vaccinebookingsystem.domain.dto.ScheduleDto;
import com.backend.vaccinebookingsystem.repository.BookingRepository;
import com.backend.vaccinebookingsystem.repository.ScheduleRepository;
import com.backend.vaccinebookingsystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createBooking(BookingDto bookingDto) {
        try {
            log.info("Creating new Booking. Value {}", bookingDto);

            Optional<BookingDao> optionalBookingDaoScheduleId = bookingRepository.findFirstByScheduleIdOrderByIdDesc(bookingDto.getSchedule().getId());
            log.info("Request Schedule Value {}", bookingDto.getSchedule().getId());
            log.info("Find first booking dao. Value {}", optionalBookingDaoScheduleId);

            Optional<ScheduleDao> optionalScheduleDao = scheduleRepository.findById(bookingDto.getSchedule().getId());
            log.info("Find schedule dao. Value {}", optionalScheduleDao);

            if (optionalBookingDaoScheduleId.isEmpty()) {
                log.info("Find first empty");
                BookingDao bookingDao = BookingDao.builder()
                        .id(bookingDto.getId())
                        .bookingPass(1)
                        .bookingDate(bookingDto.getBookingDate())
                        .schedule(optionalScheduleDao.get())
                        .build();

                bookingRepository.save(bookingDao);

                BookingDto dto = BookingDto.builder()
                        .id(bookingDao.getId())
                        .bookingPass(bookingDao.getBookingPass())
                        .bookingDate(bookingDao.getBookingDate())
                        .schedule(ScheduleDto.builder()
                                .id(bookingDao.getSchedule().getId())
                                .vaccinationDate(bookingDao.getSchedule().getVaccinationDate())
                                .operationalHourStart(bookingDao.getSchedule().getOperationalHourStart())
                                .operationalHourEnd(bookingDao.getSchedule().getOperationalHourEnd())
                                .quota(bookingDao.getSchedule().getQuota())
                                .dose(bookingDao.getSchedule().getDose())
                                .build())
                        .build();

                return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
            }

            log.info("Find first present");
            BookingDao bookingDao = BookingDao.builder()
                    .id(bookingDto.getId())
                    .bookingPass(optionalBookingDaoScheduleId.get().getBookingPass() + 1)
                    .bookingDate(bookingDto.getBookingDate())
                    .schedule(optionalScheduleDao.get())
                    .build();

            bookingRepository.save(bookingDao);

            BookingDto dto = BookingDto.builder()
                    .id(bookingDao.getId())
                    .bookingPass(bookingDao.getBookingPass())
                    .bookingDate(bookingDao.getBookingDate())
                    .schedule(ScheduleDto.builder()
                            .id(bookingDao.getSchedule().getId())
                            .vaccinationDate(bookingDao.getSchedule().getVaccinationDate())
                            .operationalHourStart(bookingDao.getSchedule().getOperationalHourStart())
                            .operationalHourEnd(bookingDao.getSchedule().getOperationalHourEnd())
                            .quota(bookingDao.getSchedule().getQuota())
                            .dose(bookingDao.getSchedule().getDose())
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

            if (optionalBookingDao.isEmpty()) {
                log.info("Booking not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking found");
            BookingDto dto = modelMapper.map(optionalBookingDao.get(), BookingDto.class);
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
                bookingDtos.add(modelMapper.map(bookingDao, BookingDto.class));
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
            if (optionalBookingDao.isEmpty()) {
                log.info("Booking not found");
                return ResponseUtil.build(AppConstant.ResponseCode.DATA_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            log.info("Booking found");
            BookingDao bookingDao = optionalBookingDao.get();
            bookingDao.setBookingPass(bookingDto.getBookingPass());
            bookingDao.setBookingDate(bookingDto.getBookingDate());
            bookingRepository.save(bookingDao);

            BookingDto dto = modelMapper.map(bookingDao, BookingDto.class);
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
