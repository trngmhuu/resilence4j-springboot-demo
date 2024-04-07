package com.fit.se.service;

import com.fit.se.dto.ResponseDto;
import com.fit.se.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public User saveUser(User user);

    public ResponseDto getUserById(int userId);

    public List<User> getAllUser();

    public void deleteUserById(int id);
}
