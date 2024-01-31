package com.scm.exceptionshandler;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class ScmExceptionHandler
{
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException authenticationException) {
        return new ResponseEntity<Object>(HttpStatusCode.valueOf(401));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        return new ResponseEntity<Object>(HttpStatusCode.valueOf(403));
    }
    
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException signatureException) {
        return new ResponseEntity<Object>(HttpStatusCode.valueOf(400));
    }
}