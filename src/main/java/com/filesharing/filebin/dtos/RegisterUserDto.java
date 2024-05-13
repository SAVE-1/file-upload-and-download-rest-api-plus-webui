package com.filesharing.filebin.dtos;

public record RegisterUserDto(
        String email,
        String password,
        String fullName

) {
    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
