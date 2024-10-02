package com.example.springairedisvectordemo.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPooled;

@Configuration
public class AppConfig {

    @Bean
    public RedisVectorStore redisVectorStore(EmbeddingModel embeddingModel, JedisConnectionFactory jedisConnectionFactory) {
        return new RedisVectorStore(RedisVectorStore.RedisVectorStoreConfig.builder()
                .withMetadataFields(RedisVectorStore.MetadataField.tag("meta2"))
                .build(),
                embeddingModel,
                new JedisPooled(jedisConnectionFactory.getHostName(),jedisConnectionFactory.getPort()),true);
    }
}
