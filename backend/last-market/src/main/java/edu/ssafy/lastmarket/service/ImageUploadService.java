package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageUploadService {

    Optional<Image> upload(MultipartFile multipartFile)throws IOException;

    List<Image> upload(MultipartFile[] multipartFiles)throws IOException;
    String findUrlById(Long id);
}
