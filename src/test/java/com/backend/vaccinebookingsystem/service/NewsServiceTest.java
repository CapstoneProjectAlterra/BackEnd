package com.backend.vaccinebookingsystem.service;

import com.backend.vaccinebookingsystem.constant.AppConstant;
import com.backend.vaccinebookingsystem.domain.common.ApiResponse;
import com.backend.vaccinebookingsystem.domain.dao.NewsDao;
import com.backend.vaccinebookingsystem.domain.dto.NewsDto;
import com.backend.vaccinebookingsystem.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = NewsService.class)
class NewsServiceTest {

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private NewsService newsService;

    @Test
    void createNewsSuccess_Test() {
        NewsDao newsDao = NewsDao.builder()
                .id(123L)
                .title("News")
                .build();

        NewsDto newsDto = NewsDto.builder()
                .id(123L)
                .title("News")
                .build();

        when(modelMapper.map(any(), eq(NewsDao.class))).thenReturn(newsDao);
        when(modelMapper.map(any(), eq(NewsDto.class))).thenReturn(newsDto);

        ResponseEntity<Object> response = newsService.createNews(NewsDto.builder()
                .title("News")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        NewsDto dto = (NewsDto) Objects.requireNonNull(apiResponse).getData();

        assertEquals(123L, dto.getId());
        assertEquals("News", dto.getTitle());
    }

    @Test
    void createNewsException_Test() {
        when(newsRepository.save(any())).thenReturn(NullPointerException.class);

        ResponseEntity<Object> response = newsService.createNews(NewsDto.builder()
                .title("News")
                .build());

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void getAllNewsSuccess_Test() {
        NewsDao newsDao = NewsDao.builder()
                .id(123L)
                .title("News")
                .build();

        when(newsRepository.findAll()).thenReturn(List.of(newsDao));
        when(modelMapper.map(any(), eq(NewsDto.class))).thenReturn(NewsDto.builder()
                .id(123L)
                .title("News")
                .build());

        ResponseEntity<Object> response = newsService.getAllNews();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(AppConstant.ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }

    @Test
    void getAllNewsException_Test() {
        when(newsRepository.findAll()).thenThrow(NullPointerException.class);

        ResponseEntity<Object> response = newsService.getAllNews();

        ApiResponse apiResponse = (ApiResponse) response.getBody();

        assertEquals(AppConstant.ResponseCode.UNKNOWN_ERROR.getCode(), Objects.requireNonNull(apiResponse).getStatus().getCode());
    }
}
