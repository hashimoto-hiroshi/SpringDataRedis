package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.demo.entity.Branch;
import com.example.demo.entity.Person;

@SpringBootTest /* (properties = "spring.profiles.active=dev") */
class RedisTemplateServiceTest {

	private final RedisTemplateService redisTemplateService;

	@Autowired
	public RedisTemplateServiceTest(RedisTemplateService redisTemplateService) {
		super();
		this.redisTemplateService = redisTemplateService;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass(@Autowired RedisTemplate<String, Object> redisTemplate) throws Exception {
		redisTemplate.keys("*").stream().forEach(e -> redisTemplate.delete(e));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		// do nothing.
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		// do nothing.
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		// do nothing.
	}

	@Test
	void test() {
		// as you like it.
	}

	@Test
	void testSetAndGet() {
		final String key = "key";
		final String value = "hoge";
		redisTemplateService.set(key, value);
		String actual = redisTemplateService.get(key);

		assertThat(actual).isEqualTo(value);
	}

	@Test
	void testHsetAndHgetall() {
		final String key = "hkey";
		Map<String, String> map = Map.of("name", "Hashimoto", "number", "100");

		redisTemplateService.hset(key, map);
		assertThat(redisTemplateService.hgetAll(key)).hasSize(2).containsAllEntriesOf(map);
	}

	@Test
	void testHsetAndHgetallForPerson() {
		final String key = "person";
		Person value = new Person();
		value.setFirstName("Hiroshi");
		value.setLastName("Hashimoto");

		redisTemplateService.hsetForPerson(key, value);
		Person actual = redisTemplateService.hgetForPerson(key);

		assertThat(actual).isEqualTo(value);
	}

	@Test
	void testHsetAndHgetallForBranch() {
		final String key = "branch";
		Branch value = new Branch();
		value.setId(3L);
		value.setBranchNo(15);
		value.setName("東京");

		redisTemplateService.hsetForBranch(key, value);
		Branch actual = redisTemplateService.hgetForBranch(key);

		assertThat(actual).isEqualTo(value);
	}

	@Test
	void testSetAndGetEntityToJson() {
		final String key = "json";
		Branch value = new Branch();
		value.setId(9L);
		value.setBranchNo(777);
		value.setName("大阪");

		redisTemplateService.setForEntityToJson(key, value);
		Branch actual = redisTemplateService.getForEntityFromJson(key);

		assertThat(actual).isEqualTo(value);
	}
}
