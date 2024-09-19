package com.example.simplewebservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//Filter Chain
		//1. All request should be authenticated
		http.authorizeHttpRequests(
				auth->auth.anyRequest().authenticated());
		//2. If a request is not authenticated, aweb page is shown.
		http.httpBasic(withDefaults());
		//3. CSRF -> POST, PUT
		return http.build();
	}
}
