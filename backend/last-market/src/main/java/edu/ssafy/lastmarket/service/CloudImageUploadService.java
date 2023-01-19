package edu.ssafy.lastmarket.service;

import java.io.File;

public interface CloudImageUploadService {
    String putImage(File uploadFile, String fileName);
}
