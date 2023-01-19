package edu.ssafy.lastmarket.controller;


import edu.ssafy.lastmarket.service.ImageUploadService;
import edu.ssafy.lastmarket.service.ImageUploadServicelogicImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageUploadService imageUploadServicelogic;

    @PostMapping("/upload")
    public ResponseEntity<?> findUpload(@RequestParam("image")MultipartFile multipartFile) throws IOException {

        String result = imageUploadServicelogic.findUrlById(imageUploadServicelogic.upload(multipartFile));

        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
