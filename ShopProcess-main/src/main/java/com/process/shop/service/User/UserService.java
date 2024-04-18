package com.process.shop.service.User;

import com.process.shop.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User createUser(User user);

    User updateUser(User userUpdated, Long id);

    User getUserById(Long id);
    void deleteUser(Long id);


    List<User> findAllUsers();

}
