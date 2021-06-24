package com.example.mongodb.service;

import com.example.mongodb.model.Message;
import com.example.mongodb.repository.HelloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class.getName());

    HelloRepository repository;

    public HelloService(HelloRepository repository) {
        this.repository = repository;
    }

    public Message saveMessage(Message message) {
        return this.repository.save(message);
    }

    public List<Message> saveAll(List<Message> characters) {

        return this.repository.saveAll(characters);
    }

    public List<Message> getAllMessages() {

        return this.repository.findAll();
    }

    public Message getMessageById(String id) {

        Message result = null;
        try {
            boolean messageExists = this.repository.existsById(id);
            if(messageExists) {
                Optional<Message> optional = this.repository.findById(id);
                if(optional.isPresent()) {
                    result = optional.get();
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Cannot supply a null id.", e);
        }
        return result;
    }

    public List<Message> getAllMessagesByContent(String matching) {

        return this.repository.findByMessageContaining(matching);
    }

    public Message updateMessage(String id, Message message) {

        Message result = null;
        message.setId(id);
        boolean messageExists = this.repository.existsById(id);
        if (messageExists) {
            result = this.repository.save(message);
        }
        return result;
    }

    public Boolean deleteMessage(String id) {

        boolean success = true;
        try {
            boolean messageExists = this.repository.existsById(id);
            if(!messageExists) {
                throw new IllegalArgumentException();
            }
            this.repository.deleteById(id);
        } catch (IllegalArgumentException e) {
            success = false;
        }
        return success;
    }

    public void deleteAllMessages() {
        this.repository.deleteAll();
    }
}
