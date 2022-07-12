package truenorth.devops.lab.VHS.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import truenorth.devops.lab.VHS.model.User;
import truenorth.devops.lab.VHS.service.UserService;

public class CustomLoginAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	/**
     * The plaintext password used to perform
     * PasswordEncoder#matches(CharSequence, String)}  on when the user is
     * not found to avoid SEC-2056.
     */
	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
	
	/**
     * The password used to perform
     * {@link PasswordEncoder#matches(CharSequence, String)} on when the user is
     * not found to avoid SEC-2056. This is necessary, because some
     * {@link PasswordEncoder} implementations will short circuit if the password is not
     * in a valid format.
     */
    private String userNotFoundEncodedPassword;
    
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    
    public CustomLoginAuthenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if(authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(
	                messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

	@Override
	protected void doAfterPropertiesSet() throws Exception {
		Assert.notNull(this.userService, "A UserService must be set.");
		this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		UserDetails loadedUser;
		
		try {
			User user = userService.getUserByUsername(username);
			if(user == null)
				throw new UsernameNotFoundException("User with given username does not exist!");
			
			String presentedPassword = authentication.getCredentials().toString();
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			
			boolean role = user.getRole().equals("ROLE_ADMIN");
			if(role) {
				authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			} else {
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			}
			
			loadedUser = new UserSecurity(user.getUsername(), user.getPassword(), role, true, true, true, true, authorities);
			
			if (!passwordEncoder.matches(presentedPassword, user.getPassword())) {
            	throw new UsernameNotFoundException("Passwords do not match!");
            }
		} catch (UsernameNotFoundException notFound) {
			if (authentication.getCredentials() != null) {
                String presentedPassword = authentication.getCredentials().toString();
                passwordEncoder.matches(presentedPassword, userNotFoundEncodedPassword);
            }
            throw notFound;
		} catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }
		
		return loadedUser;
	}

}
