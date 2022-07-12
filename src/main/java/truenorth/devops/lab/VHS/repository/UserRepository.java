package truenorth.devops.lab.VHS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import truenorth.devops.lab.VHS.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value="SELECT * FROM korisnik WHERE user_id = :id", nativeQuery=true)
	public User getUserById(@Param("id") long id);
	
	@Query(value="SELECT * FROM korisnik WHERE username = :username", nativeQuery=true)
	public User getUserByUsername(@Param("username") String username);
}