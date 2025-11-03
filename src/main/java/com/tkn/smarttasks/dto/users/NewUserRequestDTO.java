package com.tkn.smarttasks.dto.users;

public record NewUserRequestDTO (
        String email, String password, String fullName
) {
}
