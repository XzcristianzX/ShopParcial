package com.process.shop.service.User;

import com.process.shop.model.User;
import com.process.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User userUpdated, Long id) {
        Optional<User> userBd = userRepository.findById(id);
        if (userBd.isEmpty()) {
            return null; // Usuario no encontrado, puedes manejarlo como desees
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
            return userOptional.orElse(null);
        } catch (Exception e) {
            // Imprimir el mensaje de error en la consola
            e.printStackTrace();
            // Puedes lanzar una excepción personalizada o devolver un valor predeterminado según lo que prefieras
            return null;
        }
    }



    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
