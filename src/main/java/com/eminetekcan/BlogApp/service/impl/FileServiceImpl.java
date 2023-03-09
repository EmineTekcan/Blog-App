package com.eminetekcan.BlogApp.service.impl;

import com.eminetekcan.BlogApp.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //xyz.png
        String name=file.getOriginalFilename();

        String randomID= UUID.randomUUID().toString();
        String fileName=randomID.concat(name.substring(name.lastIndexOf(".")));

        String filePath=path+ File.separator+fileName;

        File file1=new File(filePath);

        if (!file1.exists())
            file1.mkdir();

        Files.copy(file.getInputStream(), Paths.get(path));
        return filePath;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath=path+File.separator+fileName;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
