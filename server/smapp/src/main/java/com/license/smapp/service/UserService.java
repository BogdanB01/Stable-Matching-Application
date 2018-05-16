package com.license.smapp.service;

import com.license.smapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService extends CrudService<User> {
    Page<User> listAllByPage(String filter, Pageable pageable);
}
