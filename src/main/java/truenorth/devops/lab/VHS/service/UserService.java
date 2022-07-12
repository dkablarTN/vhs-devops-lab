package truenorth.devops.lab.VHS.service;

import truenorth.devops.lab.VHS.model.User;

public interface UserService {

	public User getUserById(long id);
	
	public User getUserByUsername(String username);
}
