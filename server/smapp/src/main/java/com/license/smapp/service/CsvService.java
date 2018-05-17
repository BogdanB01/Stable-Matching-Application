package com.license.smapp.service;

import com.license.smapp.exception.BadRequestException;
import com.license.smapp.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CsvService {

    void parseStudentsFile(MultipartFile file);
    void parseLecturersFile(MultipartFile file);
    void parseGradesFile(MultipartFile file) throws ResourceNotFoundException;
    void parseCoursesFile(MultipartFile file);
}
