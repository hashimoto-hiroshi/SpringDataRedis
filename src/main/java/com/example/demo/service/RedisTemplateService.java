package com.example.demo.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Branch;
import com.example.demo.entity.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RedisTemplateService {

	@SuppressWarnings("unused")
	private final RedisTemplate<String, Object> redisTemplate;
	private final RedisTemplate<String, Branch> branchRedisTemplate;
	private final StringRedisTemplate stringRedisTemplate;
	private final HashOperations<String, String, Object> hashOps;
	private final Jackson2HashMapper jackson2HashMapper;

	public RedisTemplateService(RedisTemplate<String, Object> redisTemplate, StringRedisTemplate stringRedisTemplate,
			@Qualifier("redisTemplate") RedisTemplate<String, Branch> branchRedisTemplate,
			Jackson2HashMapper jackson2HashMapper, ObjectMapper objectMapper) {
		this.redisTemplate = redisTemplate;
		this.stringRedisTemplate = stringRedisTemplate;
		this.branchRedisTemplate = branchRedisTemplate;
		this.branchRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
		this.hashOps = redisTemplate.opsForHash();
		this.jackson2HashMapper = jackson2HashMapper;
	}

	/** get String */
	public String get(@NonNull final String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	/** set String */
	public void set(@NonNull final String key, @NonNull final String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	/** hgetAll */
	public Map<String, Object> hgetAll(@NonNull final String key) {
		return hashOps.keys(key).stream()
				.collect(Collectors.toMap(mapKey -> mapKey, mapKey -> hashOps.get(key, mapKey)));
	}

	/** hset by Map*/
	public void hset(@NonNull final String key, @NonNull final Map<String, String> map) {
		hashOps.putAll(key, map);
	}

	public Person hgetForPerson(String key) {
		Map<String, Object> loadedHash = hashOps.entries(key);
		return (Person) jackson2HashMapper.fromHash(loadedHash);
	}

	/** hset by Object */
	public void hsetForPerson(String key, Person person) {
		Map<String, Object> mappedHash = jackson2HashMapper.toHash(person);
		hashOps.putAll(key, mappedHash);
	}

	public Branch hgetForBranch(String key) {
		Map<String, Object> loadedHash = hashOps.entries(key);
		return (Branch) jackson2HashMapper.fromHash(loadedHash);
	}

	/** hset by Object */
	public void hsetForBranch(String key, Branch branch) {
		Map<String, Object> mappedHash = jackson2HashMapper.toHash(branch);
		hashOps.putAll(key, mappedHash);
	}

	public Branch getForEntityFromJson(String key) {
		return branchRedisTemplate.opsForValue().get(key);
	}

	/** set by Json string */
	public void setForEntityToJson(String key, Branch branch) {
		branchRedisTemplate.opsForValue().set(key, branch);
	}
}
