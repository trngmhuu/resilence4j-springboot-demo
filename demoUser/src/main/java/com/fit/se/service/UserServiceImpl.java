package com.fit.se.service;

import com.fit.se.dto.DepartmentDto;
import com.fit.se.dto.ResponseDto;
import com.fit.se.dto.UserDto;
import com.fit.se.entity.User;
import com.fit.se.repository.UserRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Retry(name = "retryApi")
    @Override
    public ResponseDto getUserById(int userId) {
        ResponseDto responseDto = new ResponseDto();
        User user = userRepository.findById(userId).get();
        UserDto userDto = mapToUser(user);
        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/departments/" + user.getDepartmentId(), DepartmentDto.class);
        DepartmentDto departmentDto = responseEntity.getBody();
        System.out.println(responseEntity.getStatusCode());
        responseDto.setUserDto(userDto);
        responseDto.setDepartmentDto(departmentDto);
        return responseDto;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    private UserDto mapToUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }
}
