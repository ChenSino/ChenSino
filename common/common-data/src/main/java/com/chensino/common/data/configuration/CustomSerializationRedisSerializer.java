package com.chensino.common.data.configuration;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class CustomSerializationRedisSerializer<T> implements RedisSerializer<T> {
    @Override
    public byte[] serialize(T o) throws SerializationException {
        return new byte[0];
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
