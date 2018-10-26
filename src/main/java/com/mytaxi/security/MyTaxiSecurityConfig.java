package com.mytaxi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mytaxi.controller.mapper.UserMapper;
import com.mytaxi.service.user.UserService;

@Configuration
@EnableWebSecurity
public class MyTaxiSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthEntryPoint authEntryPoint;

	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// Allows access to in-memory H2 DB using console
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		httpSecurity.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests()
				.antMatchers("/console/**").permitAll();

		// Setting all request to be authenticated using AuthEntryPoint
		httpSecurity.authorizeRequests().anyRequest().authenticated().and().httpBasic()
				.authenticationEntryPoint(authEntryPoint);

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		// {noop} -> Password stored in plain text
		// builder.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("USER");

		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> mngConfig = builder.inMemoryAuthentication();

		// Adding all the users from User table to InMemoryUserAuthentication based on role
		UserMapper.makeUserDTOList(userService.getUsers()).forEach(user -> {
			UserDetails inMemUser = User.withUsername(user.getUsername())
					.password(this.passwordEncoder().encode(user.getPassword())).roles(user.getRole()).build();
			mngConfig.withUser(inMemUser);
		});

	}

}
