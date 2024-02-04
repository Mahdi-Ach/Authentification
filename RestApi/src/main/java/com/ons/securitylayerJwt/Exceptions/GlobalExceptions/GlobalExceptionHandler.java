package com.ons.securitylayerJwt.Exceptions.GlobalExceptions;

import com.ons.securitylayerJwt.Exceptions.Exceptions.JwtExpiration;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> Bad_Credentiel(AuthenticationException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username Or Password Is Not Valid");
    }
    @ExceptionHandler({JwtException.class})
    public ResponseEntity<String> Unauthorized(JwtException ex){
        System.out.println("xdddd");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
