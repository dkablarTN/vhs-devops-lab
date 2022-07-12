package truenorth.devops.lab.VHS.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserSecurity extends User {

	private static final long serialVersionUID = 1L;
	
	private boolean isAdmin;
	
	public UserSecurity(String username, String password, boolean isAdmin, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.isAdmin = isAdmin;
	}

	public boolean isAdmin() {
		return this.isAdmin;
	}
}