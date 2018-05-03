package com.license.smapp.controller;

import com.license.smapp.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URLConnection;

@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {

    Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private StorageService storageService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            String filename = storageService.store(file);
            message = filename;

            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @RequestMapping(value = "/{filename:.+}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFile(@PathVariable String filename) {

        LOGGER.error("SALUT");
        storageService.deleteFile(filename);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = storageService.load(filename);
//        String mimeType = URLConnection.guessContentTypeFromName(filename);
//
//        if(mimeType == null) {
//            mimeType = "application/octet-stream";
//        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header("Access-Control-Expose-Headers", "Content-Disposition")
                .body(file);

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentLength(file.contentLength())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(new InputStreamResource(file.getInputStream()));
    }


}