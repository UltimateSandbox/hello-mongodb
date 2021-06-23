package com.example.mongodb.controller;

import com.example.mongodb.model.Message;
import com.example.mongodb.service.HelloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    // Create
    @PostMapping(value = "/addMessage", produces = "application/json")
    public Message setMessage(@RequestBody Message message) {
        return this.helloService.saveMessage(message);
    }

    @PostMapping("/addMessages")
    public List<Message> saveAll(@RequestBody List<Message> messages) {
        return helloService.saveAll(messages);
    }

    // Read
    @GetMapping(value = "/getAllMessages", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessages() {
        return this.helloService.getAllMessages();
    }

    @GetMapping(value = "/getMessageById/{id}", produces = "application/json")
    public ResponseEntity<Message> getMessageById(@PathVariable String id) {
        Message result = this.helloService.getMessageById(id);
        HttpStatus status = HttpStatus.OK;
        if (result == null) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity(result, status);
    }

    @GetMapping(value = "/getException", produces = "application/json")
    public Message getException() {
        Message message = null;
        if (message == null) {
            throw new NullPointerException("There's an issue!");
        }
        return message;
    }

    @GetMapping(value = "/getAllMessagesByContent", produces = "application/json")
    public List<Message> getAllMessagesByContent(@RequestParam(value = "match") String matching) {
        return this.helloService.getAllMessagesByContent(matching);
    }

    // Update
    @PutMapping(value = "updateMessage/{id}", produces = "application/json")
    public ResponseEntity<String> updateMessage(@PathVariable String id, @RequestBody Message message) {

        // per HTTP spec RFC 2616, Section 9.6
        Message result = this.helloService.updateMessage(id, message);
        HttpStatus status = HttpStatus.NO_CONTENT;
        String responseMessage = "";
        if (result == null) {
            responseMessage = "Invalid id!";
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(responseMessage, status);
    }

    // Delete
    @DeleteMapping(value = "deleteMessage/{id}", produces = "application/json")
    public ResponseEntity<String> deleteMessage(@PathVariable String id) {

        Boolean success = this.helloService.deleteMessage(id);
        HttpStatus status;
        String message = "";

        if (success) {
            status = HttpStatus.NO_CONTENT;
        } else {
            status = HttpStatus.NOT_FOUND;
            message = "Resource not found.";
        }

        return new ResponseEntity(message, status);
    }

    public ResponseEntity<Boolean> deleteAllMessages() {

        this.helloService.deleteAllMessages();
        return new ResponseEntity<>(true, HttpStatus.OK);

    }

}
