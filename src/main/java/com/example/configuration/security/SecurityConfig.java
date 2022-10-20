
package com.example.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.token.JWTTokenProvider;
import com.example.token.JwtAuthenticationEntryPoint;
import com.example.token.JwtAuthenticationFilter;
import com.example.utils.CustomUserDetailsService;





@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	private final JwtAuthenticationEntryPoint entryPoint;
	private final JWTTokenProvider tokenProvider;
	private final CustomUserDetailsService detailsService;

	public SecurityConfig(JwtAuthenticationEntryPoint entryPoint,
	                      JWTTokenProvider tokenProvider,
	                      CustomUserDetailsService detailsService) {
	    this.entryPoint = entryPoint;
	    this.tokenProvider = tokenProvider;
	    this.detailsService = detailsService;
	}

	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
	    return new JwtAuthenticationFilter(this.tokenProvider, this.detailsService);
	}

	
	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and()
	            .csrf().disable()
	            .exceptionHandling()
	            .authenticationEntryPoint(entryPoint)
	            .and()
	            .authorizeRequests()
	            .antMatchers("/api/v1/auth/**").permitAll()          // auth controller
	            .antMatchers("/v2/api-docs/**").permitAll()       // swagger
	            .antMatchers("/swagger-ui/**").permitAll()        // swagger
	            .antMatchers("/swagger-resources/**").permitAll() // swagger
	            .antMatchers("/swagger-ui.html").permitAll()      // swagger
	            .antMatchers("/webjars/**").permitAll()           // swagger
	            .antMatchers("/error").permitAll()
	            .anyRequest()
	            .authenticated();
	    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}
}
