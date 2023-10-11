package com.commerce.service.mail.commerceclonemail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.Duration;

@Configuration
@EnableRedisRepositories
public class RedisConfig {


    @Bean
    RedisCacheConfiguration cacheConfiguration(){

        RedisCacheConfiguration redisCacheCon =
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(10L));

        return redisCacheCon;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {

        RedisStandaloneConfiguration redisStandaloneCon = null;
        JedisConnectionFactory jedisCon = null;

        try {

            redisStandaloneCon = new RedisStandaloneConfiguration();
            redisStandaloneCon.setHostName("localhost");
            redisStandaloneCon.setPort(6379);

            jedisCon = new JedisConnectionFactory(redisStandaloneCon);

        } catch(Exception ex){
            System.out.println("Exception at jedisConnectionFactory " +ex);
        }

        return jedisCon;
    }

    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> template = null;
        try{
            template = new RedisTemplate<>();
            template.setConnectionFactory(jedisConnectionFactory());
        } catch(Exception ex) {
            System.out.println("Exception occured at redisTemplate. Error "+ex);
        }

        return template;
    }
}
