package truenorth.devops.lab.VHS.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
	
	private static final long serialVersionUID = 1L;
	private boolean isAdmin;
	
	public CustomAuthenticationToken(Object principal, Object credentials, boolean isAdmin) {
		super(principal, credentials);
		this.isAdmin = isAdmin;
		super.setAuthenticated(false);
	}
	
	public CustomAuthenticationToken(Object principal, Object credentials, boolean isAdmin, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.isAdmin = isAdmin;
		super.setAuthenticated(true);
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
}