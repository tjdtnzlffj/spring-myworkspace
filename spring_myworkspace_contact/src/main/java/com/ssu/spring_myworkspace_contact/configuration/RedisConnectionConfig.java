package com.ssu.spring_myworkspace_contact.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

// @Configuration ������̼��� @Bean Ÿ���� ������ ��ó�� ������ �� ����
// @Bean�� ���� �޼����� ���� ��ü�� ������ IoC �����̳ʿ� ���ؼ� �����ǰ� ������.
// @Autowired�� @Bean ���� ��ü Ÿ�԰� ���� ���� ������ ������ ������.

@Configuration
public class RedisConnectionConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName("127.0.0.1");
		redisStandaloneConfiguration.setPort(6379);
		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		return connectionFactory;
	}

}
