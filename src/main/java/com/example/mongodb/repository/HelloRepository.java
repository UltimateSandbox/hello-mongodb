package com.example.mongodb.repository;

import com.example.mongodb.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HelloRepository extends MongoRepository<Message, String> {

    List<Message> findByMessageContaining(String matching);
}
