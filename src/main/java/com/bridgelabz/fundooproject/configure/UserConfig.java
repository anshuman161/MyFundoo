package com.bridgelabz.fundooproject.configure;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class UserConfig 
{     
	@Bean
	public ModelMapper modelMapper() 
	{
	    return new ModelMapper();
	}
	
	@Bean
	public BCryptPasswordEncoder getBcrypt()
	{
		return new BCryptPasswordEncoder();
	}
	
	
	  @Bean 
	  public Docket productApi()	
	  { 
		 return new Docket(DocumentationType.SWAGGER_2).select()
	  .apis(RequestHandlerSelectors.basePackage("com.bridgelabz.fundooproject")).
	  build();
	 }
	 
	  @Bean
		JedisConnectionFactory jedisConnectionFactory() {
			return new JedisConnectionFactory();
		}

		@Bean
		public RedisTemplate<String, String> redisTemplate() {
			final RedisTemplate<String, String> template = new RedisTemplate<String, String>();
			template.setConnectionFactory(jedisConnectionFactory());
			template.setValueSerializer(new GenericToStringSerializer<String>(String.class));
			return template;
		}

}
