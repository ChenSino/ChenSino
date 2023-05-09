package com.chensino.common.data.configuration.cache;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@EnableCaching
@Configuration
//@EnableRedisHttpSession
public class RedisConfiguration {

    @Primary
    @Bean(name = "jedisPoolConfig")
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }


    /**
     * 配置redisTemplate针对不同key和value场景下不同序列化的方式
     * @param redisConnectionFactory Redis连接工厂
     * @return 操作
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 使用StringRedisSerializer来序列化和反序列化redis的key值，方便看懂key值
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //value未设置序列化器，使用默认的，在RedisTemplate中默认为JdkSerializationRedisSerializer
        return redisTemplate;
    }
    @Bean
    public Jackson2JsonRedisSerializer<Object> serializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        return new Jackson2JsonRedisSerializer<>(objectMapper,Object.class);
    }

    @Bean
    IGlobalCache cache(RedisTemplate<String,Object> redisTemplate) {
        return new AppRedisCacheManager(redisTemplate);
    }

}