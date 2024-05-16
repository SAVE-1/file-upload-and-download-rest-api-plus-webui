package com.filesharing.filebin.dtos;

public record LoginUserDto(
        String email,
        String password
) {
    @Override
    public String toString() {
        return String.
                format("LoginUserDto={email={0}, password='{1}'}",
                        this.email,
                        this.password);

    }
}
