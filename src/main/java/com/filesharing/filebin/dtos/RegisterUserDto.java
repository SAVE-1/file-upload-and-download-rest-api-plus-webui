package com.filesharing.filebin.dtos;

public record RegisterUserDto(
        String email,
        String password,
        String fullName

) {
    @Override
    public String toString() {
        return String.
                format("RegisterUserDto={email={0}, password='{1}', fullName={2}}",
                        this.email,
                        this.password,
                        this.fullName);

    }
}
