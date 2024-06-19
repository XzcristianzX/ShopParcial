package com.process.shop.service.User;

import com.process.shop.model.User;
import com.process.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByCc(user.getCc());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El numero de documento ya existe.");
        }String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User userUpdated, Long id) {
        Optional<User> userBd = userRepository.findById(id);
        if (userBd.isEmpty()) {
            throw new NoSuchElementException("El usuario con ID " + id + " no fue encontrado");
        }
        User existingUser = userBd.get();
        existingUser.setFullName(userUpdated.getFullName());
        existingUser.setPhoneNumber(userUpdated.getPhoneNumber());
        return userRepository.save(existingUser);
    }

    @Override
    public User getUserById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                throw new NoSuchElementException("El usuario con ID " + id + " no fue encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userBd = userRepository.findById(id);
        if (userBd.isEmpty()) {
            throw new NoSuchElementException("El usuario con ID " + id + " no fue encontrado");
        }
        userRepository.deleteById(id);
    }


    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
