package com.randstad.events.domain.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryImageService {
    Map upload(MultipartFile file);
}
