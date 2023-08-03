package com.example.demo.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RedisTemplateService {

	@SuppressWarnings("unused")
	private final RedisTemplate<String, String> redisTemplate;

	private final ValueOperations<String, String> valueOps;

	private final HashOperations<String, String, Object> hashOps;

	public RedisTemplateService(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.valueOps = redisTemplate.opsForValue();
		this.hashOps = redisTemplate.opsForHash();
	}

	public String get(@NonNull final String key) {
		return valueOps.get(key);
	}

	public void set(@NonNull final String key, @NonNull final String value) {
		valueOps.set(key, value);
	}

	public Map<String, Object> hgetAll(@NonNull final String key) {
		return hashOps.keys(key).stream()
				.collect(Collectors.toMap(mapKey -> mapKey, mapKey -> hashOps.get(key, mapKey)));
	}

	public void hset(@NonNull final String key, @NonNull final Map<String, String> map) {
		hashOps.putAll(key, map);
	}
}
