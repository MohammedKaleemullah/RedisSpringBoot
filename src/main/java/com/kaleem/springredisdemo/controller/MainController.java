package com.kaleem.springredisdemo.controller;

import com.kaleem.springredisdemo.model.Person;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class MainController {

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Person> hashOperations;

    @GetMapping("/{key}")
    public Person getPerson(@PathVariable("key") String key) {
        System.out.println("Going to get person with id : " + key);
        return hashOperations.get("Person",key);
    }

    @PostMapping(value = "/set-person",consumes = "application/json")
    public ResponseEntity<String> setPerson(@RequestBody Person person) {
        System.out.println("Going to save person : " + person);
        hashOperations.put("Person", person.getId(), person);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}
