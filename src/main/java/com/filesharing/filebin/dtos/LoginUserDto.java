package com.filesharing.filebin.dtos;

public record LoginUserDto(
        String email,
        String password
) {
    @Override
    public String toString() {
        return "LoginUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
