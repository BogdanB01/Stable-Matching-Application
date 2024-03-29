package com.license.smapp.control.service;

import com.license.smapp.boundry.exceptions.BadRequestException;
import com.license.smapp.boundry.exceptions.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface CsvService {

    void parseStudentsFile(MultipartFile file);
    void parseLecturersFile(MultipartFile file);
    void parseGradesFile(MultipartFile file) throws ResourceNotFoundException, BadRequestException;
    void parseCoursesFile(MultipartFile file);
}
