package edu.ssafy.lastmarket.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.analytics.AnalyticsAndOperator;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloudImageUploadServiceImpl implements CloudImageUploadService{
    private static final Tika tika = new Tika();
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    public String bucket; // S3 버킷 이름

    @Override
    public String putImage(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    @Override
    public void delete(String fileName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, fileName);
        S3Object object = amazonS3Client.getObject(bucket, fileName);
        amazonS3Client.deleteObject(deleteObjectRequest);

    }

    public static boolean validImgFile(InputStream inputStream) throws Exception{
        List<String> notValidTypeList = Arrays.asList("image/jpeg", "image/pjpeg", "image/png");

        String mimeType = tika.detect(inputStream);

        boolean isValid = notValidTypeList.stream().anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));

        return isValid;
    }
}
