package com.dentaltechapi.service.services.user;

import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.repository.repositories.user.UserRepository;
import com.dentaltechapi.service.exceptions.user.UserCreationException;
import com.dentaltechapi.service.exceptions.user.UserNotFoundException;
import com.dentaltechapi.service.exceptions.user.UserUpdateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel findByUserUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (NoSuchElementException exception) {
            throw new UserNotFoundException("Usuário não encontrado.", exception.getCause());
        }
    }

    public UserModel createNewUser(UserModel userModel) {
        try {
            return userRepository.save(userModel);
        } catch (IllegalArgumentException exception) {
            throw new UserCreationException("Erro ao cadastrar usuário", exception.getCause());
        }
    }

    public String updateUserPassword(Long id, String password) {
        try {
            UserModel persistedUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("usuário não encontrado."));

            persistedUser.setPassword(new BCryptPasswordEncoder().encode(password));
            userRepository.save(persistedUser);

            return persistedUser.getEmail();
        } catch (UserNotFoundException | IllegalArgumentException exception) {
            throw new UserUpdateException("Erro ao atualizar usuário.", exception.getCause());
        }
    }

    public Boolean verifyExistingUser(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean verifyExistingEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
