package com.example.dogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class DogsApplication {

	@Configuration
	public class SecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.anyRequest().authenticated()
					.and()
					.oauth2Login();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DogsApplication.class, args);
	}

}
