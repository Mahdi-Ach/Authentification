package com.ons.securitylayerJwt.Exceptions.Exceptions;

public class JwtExpiration extends Exception{
    public JwtExpiration(String errorMessage) {
        super(errorMessage);
    }
}
