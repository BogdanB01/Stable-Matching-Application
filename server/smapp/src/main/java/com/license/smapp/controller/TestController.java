package com.license.smapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/api")
public class TestController {

    @RequestMapping(method = RequestMethod.GET, value = "/test/student")
    @PreAuthorize("hasRole('STUDENT')")
    public String test() {
        return "STUDENTULE";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/test/profesor")
    @PreAuthorize("hasRole('LECTURER')")
    public String testLect() {
        return "PROFESORULE";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String testAdmin() {
        return "PROFESORULE";
    }


}

