package com.license.smapp.control.service.impl;

import com.license.smapp.control.service.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation = Paths.get("upload-dir");

    @Override
    public void init() {
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if(file.isEmpty()) {
                throw  new RuntimeException("Empty file!");
            }

            String basename = FilenameUtils.getBaseName(file.getOriginalFilename());
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            String filename = file.getOriginalFilename();

            for (int num = 2; Files.exists(this.rootLocation.resolve(filename)); num++) {
                filename = basename + " (" + num  + ")." + extension;
            }

            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename));

            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files");
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw  new RuntimeException("Fail");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Fail");
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
//        try {
//            Path file = load(filename);
//            Resource resource = new UrlResource(file.toUri());
//            if(resource.exists() || resource.isReadable()) {
//                return resource;
//            } else {
//                throw new RuntimeException("Could not read file " + filename);
//            }
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Could not read file " + filename);
//        }
        return null;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Files.delete(this.rootLocation.resolve(filename));
        } catch (IOException e) {
            //throw new StorageFileNotFoundException("File not found!");
        }
    }
}
