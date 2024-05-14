package com.filesharing.filebin.dtos;

import java.text.MessageFormat;

public record LoginUserDto(
        String email,
        String password
) {
    @Override
    public String toString() {
        return MessageFormat.
                format("LoginUserDto={email={0}, password='{1}'}",
                        this.email,
                        this.password);

    }
}
