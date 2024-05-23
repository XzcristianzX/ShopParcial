package com.process.shop.service.userTest;

import com.process.shop.model.User;
import com.process.shop.model.enunm.DocumentType;
import com.process.shop.repository.UserRepository;
import com.process.shop.service.User.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setCc("123456789");
        user.setFullName("John Doe");
        user.setCcType(DocumentType.CC);
        user.setBirthDay(LocalDate.of(1990, 1, 1));
        user.setPhoneNumber("1234567890");
        user.setEmail("john@example.com");
        user.setPassword("password");

        when(userRepository.findByCc(user.getCc())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("123456789", result.getCc());

        verify(userRepository, times(1)).findByCc(user.getCc());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUserAlreadyExists() {
        User user = new User();
        user.setCc("123456789");

        when(userRepository.findByCc(user.getCc())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(user);
        });

        assertEquals("El número de documento ya existe.", exception.getMessage());

        verify(userRepository, times(1)).findByCc(user.getCc());
        verify(userRepository, never()).save(user);
    }

    // Resto de los métodos de prueba...
}
