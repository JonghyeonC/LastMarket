package edu.ssafy.lastmarket.service;

import edu.ssafy.lastmarket.domain.entity.Image;
import edu.ssafy.lastmarket.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploadServiceImpl implements ImageUploadService{

    private final CloudImageUploadService cloudImageUploadService;
    private final ImageRepository imageRepository;
    private final EntityManager entityManager;



    // S3로 파일 업로드하기
    @Override
    @Transactional
    public Optional<Image> upload(MultipartFile multipartFile) throws IOException {

        String originFileName = multipartFile.getName();
        File uploadFile = convert(multipartFile);
        String fileName = "files/" + UUID.randomUUID() + uploadFile.getName(); // S3에 저장된 파일 이름
        String uploadImageUrl = cloudImageUploadService.putImage( uploadFile, fileName); // s3로 업로드

        Image image = new Image(originFileName, uploadImageUrl);
        imageRepository.save(image);
        entityManager.flush();

        Optional<Image> imageOptional = imageRepository.findByImageURL(uploadImageUrl);

        removeNewFile(uploadFile);

        return imageOptional;
    }

    // S3로 업로드


    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {

        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
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




}
