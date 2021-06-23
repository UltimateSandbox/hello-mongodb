package com.example.mongodb.bootstrap;

import com.example.mongodb.controller.HelloController;
import com.example.mongodb.model.Message;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BootStrap implements CommandLineRunner {

    private final HelloController helloController;

    public BootStrap(HelloController helloRepository) {

        this.helloController = helloRepository;
    }

    @Override
    public void run(String... args) {

        // clear old data
        helloController.deleteAllMessages();

        // create messages
        List<Message> messages = Arrays.asList(
                new Message("Hello!"),
                new Message("Howdy!"),
                new Message("Aloha!"),
                new Message("Greetings!"),
                new Message("Hi!")
        );

        // save messages
        helloController.saveAll(messages)
                .stream()
                .forEach(System.out::println);

    }
}
