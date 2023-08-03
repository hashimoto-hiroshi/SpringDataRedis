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
import org.springframework.data.redis.hash.Jackson2HashMapper;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
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
	void test() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Hashimoto");
		employee.setDepartment(Department.GENERAL);

		System.out.println(employee.toString());

		System.out.println("---");

		JsonMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		System.out.println(mapper.writeValueAsString(employee));

		System.out.println("---");

		Jackson2HashMapper mapper1 = new Jackson2HashMapper(false);
		mapper1.toHash(employee).entrySet().stream()
				.forEach(e -> System.out.println("key=" + e.getKey() + ",value=" + e.getValue()));

		System.out.println("---");

		Jackson2HashMapper mapper2 = new Jackson2HashMapper(true);
		mapper2.toHash(employee).entrySet().stream()
				.forEach(e -> System.out.println("key=" + e.getKey() + ",value=" + e.getValue()));

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
}
