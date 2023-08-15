package com.crestdevs.sphinxbe.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class VerificationTokenUserDto {

    private final Integer EXP_TIME = 8640;
    private Integer id;
    private String token;
    private Date expiration;
    private UserDto user;

    public VerificationTokenUserDto() {
        expiration = calculateExpirationDate(EXP_TIME);
    }

    private Date calculateExpirationDate(int expirationTime) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}