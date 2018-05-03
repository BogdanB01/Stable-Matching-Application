package com.license.smapp.service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file);

    Stream<Path> loadAll();

    Resource load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    void deleteFile(String filename);
}
