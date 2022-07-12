package truenorth.devops.lab.VHS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import truenorth.devops.lab.VHS.security.CustomAuthenticationFilter;
import truenorth.devops.lab.VHS.security.CustomLoginAuthenticationProvider;
import truenorth.devops.lab.VHS.security.MySimpleUrlAuthenticationSuccessHandler;
import truenorth.devops.lab.VHS.service.UserService;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MySimpleUrlAuthenticationSuccessHandler successHandler;
	
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
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/homepage");
		return http.build();
	}
	
	public CustomAuthenticationFilter authenticationFilter() throws Exception {
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager(new AuthenticationConfiguration()));
		filter.setAuthenticationSuccessHandler(successHandler);
		filter.setAuthenticationFailureHandler(failureHandler());
		return filter;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	public AuthenticationProvider authProvider() {
		CustomLoginAuthenticationProvider provider 
        	= new CustomLoginAuthenticationProvider(passwordEncoder, userService);
		return provider;
	}

	public SimpleUrlAuthenticationFailureHandler failureHandler() {
		return new SimpleUrlAuthenticationFailureHandler("/homepage-error");
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}