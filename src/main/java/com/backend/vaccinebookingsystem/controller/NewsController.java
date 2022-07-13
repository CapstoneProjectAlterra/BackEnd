package com.backend.vaccinebookingsystem.controller;

import com.backend.vaccinebookingsystem.domain.dto.NewsDto;
import com.backend.vaccinebookingsystem.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/news", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewNews(@RequestBody NewsDto newsDto) {
        return newsService.createNews(newsDto);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllNews() {
        return newsService.getAllNews();
    }
}
