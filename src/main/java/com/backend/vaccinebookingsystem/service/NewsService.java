package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.dao.NewsDao;
import com.backend.vaccinebookingsystem.domain.dto.NewsDto;
import com.backend.vaccinebookingsystem.repository.NewsRepository;
import com.backend.vaccinebookingsystem.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> createNews(NewsDto newsDto) {
        try {
            log.info("Creating News");
            NewsDao newsDao = modelMapper.map(newsDto, NewsDao.class);
            newsRepository.save(newsDao);

            NewsDto dto = modelMapper.map(newsDao, NewsDto.class);
            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in creating News. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllNews() {
        try {
            log.info("Getting News");
            List<NewsDao> newsDaoList;
            List<NewsDto> newsDtos = new ArrayList<>();

            newsDaoList = newsRepository.findAll();

            for (NewsDao newsDao : newsDaoList) {
                newsDtos.add(modelMapper.map(newsDao, NewsDto.class));
            }

            return ResponseUtil.build(AppConstant.ResponseCode.SUCCESS, newsDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred in getting News. Error {}", e.getMessage());
            return ResponseUtil.build(AppConstant.ResponseCode.UNKNOWN_ERROR, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
