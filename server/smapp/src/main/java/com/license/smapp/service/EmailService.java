package com.license.smapp.service;

import com.license.smapp.common.Email;
import com.license.smapp.model.User;

public interface EmailService {

    Email getEmail(User user, String subject);

    void sendEmail(Email email);
}
