package truenorth.devops.lab.VHS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/api/vhs/new").hasRole("ADMIN")
		.antMatchers("/api/rental/**").authenticated()
		.antMatchers("/homepage*").permitAll()
		.antMatchers("logout").authenticated()
		.and()
		.formLogin()
			.loginPage("/homepage")
			.failureUrl("/hompage-error")
			.loginProcessingUrl("/login")
		.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logut"))
			.logoutSuccessUrl("/homepage");
		return http.build();
	}
}