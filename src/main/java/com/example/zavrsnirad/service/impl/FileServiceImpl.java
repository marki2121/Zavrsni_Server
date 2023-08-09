package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import com.example.zavrsnirad.service.FileService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserProfileService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private final Path PATH = Paths.get(new ClassPathResource("").getFile().getAbsolutePath() + File.separator + "files");
    private final UserGetService userGetService;
    private final UserProfileService userProfileService;

    public FileServiceImpl(UserGetService userGetService, UserProfileService userProfileService) throws IOException {
        this.userGetService = userGetService;
        this.userProfileService = userProfileService;
    }

    @Override
    public String uploadFile(String auth, MultipartFile file) throws CostumeErrorException, IOException {
        User user = userGetService.getUserFromToken(auth);
        UserProfile userProfile = user.getUserProfile();

        if (!Files.exists(PATH)) {
            Files.createDirectories(PATH);
        }

        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png")) {
            throw new CostumeErrorException("only .jpeg and .png images are " + "supported");
        }

        String extencion = file.getContentType().equals("image/jpeg") ? ".jpeg" : ".png";

        Path filePath = PATH.resolve(user.getId() + extencion);
        Files.copy(file.getInputStream(), filePath);

        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(String.valueOf(user.getId()))
                .toUriString();

        userProfile.setImageUrl(fileUri);

        userProfileService.saveProfile(userProfile);

        return "File uploaded successfully!";
    }
}
