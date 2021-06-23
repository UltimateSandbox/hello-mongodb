package com.example.mongodb.service;

import com.example.mongodb.model.Message;
import com.example.mongodb.repository.HelloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
            result = this.repository.findById(id).get();
        } catch (NoSuchElementException e) {
            logger.error("Issue getting message by Id!", e);
        }
        return result;
    }

    public List<Message> getAllMessagesByContent(String matching) {

        return this.repository.findByMessageContaining(matching);
    }

    public Message updateMessage(String id, Message message) {

        Message result = null;
        message.setId(id);
        if(messageExists(id)) {
            result = this.repository.save(message);
        }
        return result;
    }

    // Test for message existence
    private boolean messageExists(String id) {

        boolean exists = true;
        Message result = null;
        try {
            result = repository.findById(id).get();
        } catch (NoSuchElementException e) {
            exists = false;
        }

        if(result == null) {
            exists = false;
        }

        return exists;
    }

    public Boolean deleteMessage(String id) {
        boolean success = true;
        try {
            this.repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            success = false;
        }
        return success;
    }

    public void deleteAllMessages() {
        this.repository.deleteAll();
    }
}
