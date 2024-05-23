package com.process.shop.service.userTest;
import com.process.shop.service.User.UserService;
import com.process.shop.model.User;
import com.process.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByCc(user.getCc());
        if (existingUser.isPresent()) {
            throw new RuntimeException("El número de documento ya existe.");
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }


    @Override
    public User updateUser(User userUpdated, Long id) {
        Optional<User> userBd = userRepository.findById(id);
        if (userBd.isEmpty()) {
            return null;
        }
        User existingUser = userBd.get();
        existingUser.setFullName(userUpdated.getFullName());
        existingUser.setCcType(userUpdated.getCcType());
        existingUser.setCc(userUpdated.getCc());
        existingUser.setBirthDay(userUpdated.getBirthDay());
        existingUser.setPhoneNumber(userUpdated.getPhoneNumber());
        existingUser.setEmail(userUpdated.getEmail());
        existingUser.setPassword(userUpdated.getPassword());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return List.of();
    }
}
