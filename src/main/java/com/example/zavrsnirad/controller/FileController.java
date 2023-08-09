package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    private ResponseEntity<String> uploadFile(@RequestHeader String Authorization, @RequestParam MultipartFile files) throws CostumeErrorException, IOException {
        return ResponseEntity.ok(fileService.uploadFile(Authorization, files));
    }
}