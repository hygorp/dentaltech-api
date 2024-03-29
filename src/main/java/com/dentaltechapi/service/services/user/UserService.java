package com.dentaltechapi.service.services.user;

import com.dentaltechapi.model.entities.user.UserModel;
import com.dentaltechapi.repository.repositories.user.UserRepository;
import com.dentaltechapi.service.exceptions.UserServiceException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (NoSuchElementException exception) {
            throw new UserServiceException("Usuário não encontrado.");
        }
    }

    public UserModel createNewUser(UserModel userModel) {
        try {
            return userRepository.save(userModel);
        } catch (IllegalArgumentException exception) {
            throw new UserServiceException("Erro ao cadastrar usuário", exception.getCause());
        }
    }

    public Boolean verifyExistingUser(String username) {
        return userRepository.existsByUsername(username);
    }
}
