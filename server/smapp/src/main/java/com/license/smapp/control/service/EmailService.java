package com.license.smapp.control.service;

import com.license.smapp.common.Email;
import com.license.smapp.entity.model.User;

public interface EmailService {

    Email getEmail(User user, String subject);

    void sendEmail(Email email);
}
