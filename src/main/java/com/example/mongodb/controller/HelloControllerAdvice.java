package com.example.mongodb.controller;

import com.example.mongodb.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class HelloControllerAdvice {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Message> resourceNotFoundException(Exception ex) {

        String id = UUID.randomUUID().toString();
        String message = "Help me!";
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        Message responseMessage = new Message(id, message);

        return new ResponseEntity<>(responseMessage, status);
    }
}
