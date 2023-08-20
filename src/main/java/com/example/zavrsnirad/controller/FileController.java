package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@CrossOrigin
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

    @GetMapping("/download/{filename}")
    private ResponseEntity<Object> downlaodFile(@PathVariable String filename) throws FileNotFoundException {
        Resource resource = fileService.downloadFile(filename);
        String mime = resource.getDescription().substring(resource.getDescription().lastIndexOf(".") + 1);
        mime = mime.substring(0, mime.length() - 1);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/" + mime))
                .body(resource);
    }
}
