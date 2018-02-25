package com.license.StableMatchingApplication.services;

import com.license.StableMatchingApplication.models.User;
import com.license.StableMatchingApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public interface UserService extends CrudService<User>{

    /**
     * @return Returns a list with all users
     */
    List<User> getAllUsers();

}
