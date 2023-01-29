package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageUploadService {

    Optional<Image> upload(MultipartFile multipartFile)throws IOException;

    List<Image> upload(MultipartFile[] multipartFiles)throws IOException;

    void delete(Image image);
    void delete(List<Image> images);
    String findUrlById(Long id);
}
