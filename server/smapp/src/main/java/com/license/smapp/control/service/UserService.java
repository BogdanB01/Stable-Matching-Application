package com.license.smapp.control.service;

import com.license.smapp.entity.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService extends CrudService<User> {
    Page<User> listAllByPage(String filter, Pageable pageable);
}
