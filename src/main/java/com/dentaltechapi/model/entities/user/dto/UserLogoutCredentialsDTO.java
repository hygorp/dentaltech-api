package com.dentaltechapi.model.entities.user.dto;

import java.util.UUID;

public record UserLogoutCredentialsDTO(String username, UUID sessionId) {
}
