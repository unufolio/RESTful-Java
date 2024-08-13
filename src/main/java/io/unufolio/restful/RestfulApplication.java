package io.unufolio.restful;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(RestfulApplication.class, args);
    }

}
