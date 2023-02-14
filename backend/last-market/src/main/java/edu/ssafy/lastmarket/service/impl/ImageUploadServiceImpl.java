package edu.ssafy.lastmarket.service.impl;

import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.domain.entity.ProductImage;
import edu.ssafy.lastmarket.repository.ImageRepository;
import edu.ssafy.lastmarket.service.CloudImageUploadService;
import edu.ssafy.lastmarket.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import magick.*;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploadServiceImpl implements ImageUploadService {

    private final CloudImageUploadService cloudImageUploadService;
    private final ImageRepository imageRepository;
    private final EntityManager entityManager;



    // S3로 파일 업로드하기
    @Override
    @Transactional
    public Optional<Image> upload(MultipartFile multipartFile) throws IOException {

        String originFileName = multipartFile.getOriginalFilename();
        File uploadFile = convert(multipartFile);


        String fileName = "files/" + UUID.randomUUID() + uploadFile.getName(); // S3에 저장된 파일 이름
        String uploadImageUrl = cloudImageUploadService.putImage( uploadFile, fileName); // s3로 업로드

        File s_uploadFile = changeFileQualitu(uploadFile);
        cloudImageUploadService.putImage( s_uploadFile, fileName);


        Image image = new Image(originFileName, uploadImageUrl);
        imageRepository.save(image);
        entityManager.flush();

        Optional<Image> imageOptional = imageRepository.findByImageURL(uploadImageUrl);

        removeNewFile(uploadFile);
        return imageOptional;
    }

    @Override
    @Transactional
    public List<Image> upload(MultipartFile[] multipartFiles) throws IOException {
        List<Image> result = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            Optional<Image> upload = upload(multipartFile);
            result.add(upload.get());
        }

        return result;
    }

    @Override
    @Transactional
    public void delete(Image image) {
        imageRepository.save(image);
//        cloudImageUploadService.delete(image.getImageURL());

    }

    @Override
    @Transactional
    public List<Image> delete(List<Image> images) {
        imageRepository.deleteAll(images);
        return images;
    }

    // 로컬에 저장된 이미지 지우기
    //TODO : 수정하기
    private void removeNewFile(File targetFile) {
        targetFile.delete();
    }

    // 로컬에 파일 업로드 하기
    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @Override
    public String findUrlById(Long id) {
        return imageRepository.findById(id).get().getImageURL();
    }

    private File changeFileFormat(File file) throws MagickException {


        return null;
    }

    private File changeFileQualitu(File file) throws IOException {

        //섬네일 생성 -> 섬네일 파일 이름은 중간에 s_로 시작
        String thubmnailSaveName = "s_" +file.getName();

        File thumbnailFile = new File(thubmnailSaveName);
        // 섬네일 생성
        Thumbnailator.createThumbnail(file,thumbnailFile,256,256);

        return thumbnailFile;

    }


}
