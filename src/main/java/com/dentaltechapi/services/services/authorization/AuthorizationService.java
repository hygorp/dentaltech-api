package com.dentaltechapi.services.services.authorization;

import com.dentaltechapi.repository.repositories.user.UserRepository;
import com.dentaltechapi.services.exceptions.authorization.AuthorizationServiceException;
import com.dentaltechapi.services.exceptions.user.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (UserNotFoundException exception) {
            throw new AuthorizationServiceException("Usuário não encontrado", exception.getCause());
        }
    }
}
