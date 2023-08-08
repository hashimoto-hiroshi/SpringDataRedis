package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
class RedisConfig {

	@Bean
	LettuceConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
	}

	@Bean
	RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();

		template.setConnectionFactory(redisConnectionFactory);

		template.setKeySerializer(RedisSerializer.string());
		template.setValueSerializer(RedisSerializer.string());
		template.setHashKeySerializer(RedisSerializer.string());
		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));

		template.afterPropertiesSet();

		return template;
	}

	@Bean
	ObjectMapper objectMapper() {
		return JsonMapper.builder().addModule(new JavaTimeModule())
				.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.activateDefaultTyping(BasicPolymorphicTypeValidator.builder()
						.allowIfSubType("com.example.demo.entity.")
						.build(),
						ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT, As.PROPERTY)
				.build();
	}

	@Bean
	Jackson2HashMapper jackson2HashMapper() {
		return new Jackson2HashMapper(true);
	}
}
