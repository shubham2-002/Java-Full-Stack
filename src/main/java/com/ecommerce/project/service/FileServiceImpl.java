package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {
        //Gettig file name
        String OgfileName = image.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        // mat.png ->1234 ->1234.png
        String uniqueFilename = randomId.concat(OgfileName.substring(OgfileName.lastIndexOf(".")));

        //Uploda to server and remane file uniquely

        File folder =  new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }

        String filePath = Paths.get(path, uniqueFilename).toString();
        //upload to server
        Files.copy(image.getInputStream(), Paths.get(filePath));
        return uniqueFilename;
    }
}
