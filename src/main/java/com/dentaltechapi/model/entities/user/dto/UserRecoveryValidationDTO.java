package com.dentaltechapi.model.entities.user.dto;

public record UserRecoveryValidationDTO(String username, String password, Integer code) {
}
