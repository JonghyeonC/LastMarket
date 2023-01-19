package edu.ssafy.lastmarket.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {

    Long upload(MultipartFile multipartFile)throws IOException;
    String findUrlById(Long id);
}
