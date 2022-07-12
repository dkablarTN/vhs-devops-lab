package truenorth.devops.lab.VHS.service;

import org.springframework.stereotype.Service;

import truenorth.devops.lab.VHS.model.User;
import truenorth.devops.lab.VHS.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository iUserRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.iUserRepository = userRepository;
	}
	
	@Override
	public User getUserById(long id) {
		return iUserRepository.getUserById(id);
	}

	@Override
	public User getUserByUsername(String username) {
		return iUserRepository.getUserByUsername(username);
	}

}
