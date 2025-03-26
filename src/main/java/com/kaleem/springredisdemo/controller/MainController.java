package com.kaleem.springredisdemo.controller;

import com.kaleem.springredisdemo.model.Person;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class MainController {

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Person> hashOperations;
    @Autowired
    private RedisTemplate<String, Person> redisTemplate;

    @GetMapping("/{key}")
    public Person getPerson(@PathVariable("key") String key) {
        System.out.println("Going to getPerson : "+ key);
        Person person = hashOperations.get("Person", key);
        System.out.println("Value found is " + person);

        if(ObjectUtils.isEmpty(person)){
            System.out.println("Person not found in redis ");
            //Implement fetch from database and add in redis using hashOperations.put
        }
        return person;
    }

    @DeleteMapping("/del/{key}")
    public ResponseEntity<String> delPerson(@PathVariable("key") String key) {
        System.out.println("Going to delPerson : "+ key);
        long Long = hashOperations.delete("Person", key);
        System.out.println("Person deleted successfully");
        return ResponseEntity.ok("Person deleted successfully:"+Long);
    }

    @GetMapping("/")
    public Map<String, Person> getPersons() {
        System.out.println("Going to get persons");

        return hashOperations.entries("Person");
    }

    @PostMapping(value = "/set-person",consumes = "application/json")
    public ResponseEntity<String> setPerson(@RequestBody Person person) {
        System.out.println("Going to save person : " + person);
        hashOperations.put("Person", person.getId(), person);

//        redisTemplate.expire("Person",
//                1,
//                TimeUnit.MINUTES);
        return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
    }

}
