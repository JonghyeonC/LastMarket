package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageUploadService {

    Optional<Image> upload(MultipartFile multipartFile)throws IOException;
    String findUrlById(Long id);
}
