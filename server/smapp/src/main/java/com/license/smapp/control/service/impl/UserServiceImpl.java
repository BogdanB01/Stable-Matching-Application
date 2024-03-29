package com.license.smapp.control.service.impl;


import com.license.smapp.entity.model.User;
import com.license.smapp.entity.repository.UserRepository;
import com.license.smapp.control.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User entity) {
        return this.userRepository.save(entity);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        this.userRepository.delete(id);
    }


    @Override
    public Page<User> listAllByPage(String filter, Pageable pageable) {
        // return this.userRepository.findAllByNameStartingWith(filter);
     //  return new PageImpl<User>(this.userRepository.findAllByNameStartingWith(filter));
        return this.userRepository.findAllByEmailStartingWith(filter, pageable);
    }
}
