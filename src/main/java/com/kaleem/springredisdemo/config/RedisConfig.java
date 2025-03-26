package com.kaleem.springredisdemo.config;

import com.kaleem.springredisdemo.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory getConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        return factory;
    }

    @Bean
    public RedisTemplate<String, Person> redisTemplate() {
        RedisTemplate<String, Person> template = new RedisTemplate<String, Person>();
        template.setConnectionFactory(getConnectionFactory());
        return template;
    }
}
