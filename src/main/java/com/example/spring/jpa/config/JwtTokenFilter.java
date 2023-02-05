package com.example.spring.jpa.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.spring.jpa.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private DefaultJwtService jwtService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    
    private static final String HEADER = "Authorization";
    
	  @Override
	  protected void doFilterInternal(
	      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	      throws ServletException, IOException {
	    
		  getTokenString(request.getHeader(HEADER))
	        .flatMap(jwtService::getSubFromToken)
	        .ifPresent(
	            id -> {
	              if (SecurityContextHolder.getContext().getAuthentication() == null) {
	                userRepository
	                    .findById(Long.valueOf(id))
	                    .ifPresent(
	                        user -> {	                          
	                        UserDetails userDetails = customUserDetailsService.loadUserById(Long.valueOf(id));
	                        
	                        UsernamePasswordAuthenticationToken authenticationToken =
		                              new UsernamePasswordAuthenticationToken(userDetails , null, userDetails.getAuthorities());
	                        
	                          authenticationToken.setDetails(  new WebAuthenticationDetailsSource().buildDetails(request) );
	                          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	                        });
	              }
	            });

	    filterChain.doFilter(request, response);
	  }
	
    private Optional<String> getTokenString(String header) {
        if (header == null) {
          return Optional.empty();
        } else {
          String[] split = header.split(" ");
          if (split.length < 2) {
            return Optional.empty();
          } else {
            return Optional.ofNullable(split[1]);
          }
        }
      }
	
}