package com.example.spring.jpa.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	  @Bean
	  public JwtTokenFilter jwtTokenFilter() {
	    return new JwtTokenFilter();
	  }
	  

	  protected void configure(HttpSecurity http) throws Exception {

	    http.csrf()
	        .disable()
	        .cors()
	        .and()
	        .exceptionHandling()
	        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
	        .and()
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeHttpRequests(
	        		authorize -> authorize
	        			.requestMatchers(HttpMethod.OPTIONS).permitAll()
	        			.requestMatchers(HttpMethod.GET,"/articles/feed").authenticated()
	        			.requestMatchers(HttpMethod.POST, "/users",  "/users/login").permitAll()
	        			.requestMatchers(HttpMethod.GET, "/articles/**", "/profiles/**", "/tags").permitAll()
				        .anyRequest().authenticated()
				        );

	    http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	  }
	  
	  @Bean
	  public CorsConfigurationSource corsConfigurationSource() {
	    final CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
	    // setAllowCredentials(true) is important, otherwise:
	    // The value of the 'Access-Control-Allow-Origin' header in the response must not be the
	    // wildcard '*' when the request's credentials mode is 'include'.
	    configuration.setAllowCredentials(false);
	    // setAllowedHeaders is important! Without it, OPTIONS preflight request
	    // will fail with 403 Invalid CORS request
	    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	  }
}
