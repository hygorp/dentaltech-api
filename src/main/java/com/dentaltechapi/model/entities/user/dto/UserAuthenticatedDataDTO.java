package com.dentaltechapi.model.entities.user.dto;

import java.util.UUID;

public record UserAuthenticatedDataDTO(String username, String role, String token, UUID sessionId) {
}
