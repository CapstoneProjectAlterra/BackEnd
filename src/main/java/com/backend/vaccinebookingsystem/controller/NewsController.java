package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.NewsDto;
import com.backend.vaccinebookingsystem.service.NewsService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/news", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    @Autowired
    private NewsService newsService;


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = NewsDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Object> createNewNews(@RequestBody NewsDto newsDto) {
        return newsService.createNews(newsDto);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = NewsDto.class),
            @ApiResponse(code = 400, message = "Data not found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllNews() {
        return newsService.getAllNews();
    }
}
