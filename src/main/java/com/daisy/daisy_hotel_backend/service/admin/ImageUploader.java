package com.daisy.daisy_hotel_backend.service.admin;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {
    String uploadImage(MultipartFile file);
}
