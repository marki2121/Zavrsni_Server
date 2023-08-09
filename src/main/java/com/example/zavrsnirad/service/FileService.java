package com.example.zavrsnirad.service;

import com.example.zavrsnirad.entity.CostumeErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(String auth, MultipartFile files) throws CostumeErrorException, IOException;
}
