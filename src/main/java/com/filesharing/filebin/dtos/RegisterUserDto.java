package com.filesharing.filebin.dtos;

import java.text.MessageFormat;

public record RegisterUserDto(
        String email,
        String password,
        String fullName

) {
    @Override
    public String toString() {
        return MessageFormat.
                format("RegisterUserDto={email={0}, password='{1}', fullName={2}}",
                        this.email,
                        this.password,
                        this.fullName);

    }
}
