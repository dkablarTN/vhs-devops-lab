package truenorth.devops.lab.VHS.bootstrap;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import truenorth.devops.lab.VHS.model.Rental;
import truenorth.devops.lab.VHS.model.User;
import truenorth.devops.lab.VHS.model.VHS;
import truenorth.devops.lab.VHS.repository.RentalRepository;
import truenorth.devops.lab.VHS.repository.UserRepository;
import truenorth.devops.lab.VHS.repository.VHSRepository;

@Component
public class BootStrapData implements CommandLineRunner {
	
	private final RentalRepository rentalRepository;
	private final VHSRepository vhsRepository;
	private final UserRepository userRepository;

	public BootStrapData(RentalRepository rentalRepository, VHSRepository vhsRepository,
			UserRepository userRepository) {
		super();
		this.rentalRepository = rentalRepository;
		this.vhsRepository = vhsRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		User admin = new User("admin", "$2a$10$9fIidnC6qS0V87BcUlcGx.0s7Uv6S3zaBwn5f6UF74t1h1y1L256G", "ROLE_ADMIN", true, new ArrayList<>());
		User user = new User("mmistric", "$2a$10$9fIidnC6qS0V87BcUlcGx.0s7Uv6S3zaBwn5f6UF74t1h1y1L256G", "ROLE_USER", true, new ArrayList<>());
		
		userRepository.save(admin);
		userRepository.save(user);
		
		VHS lotr1 = new VHS("Fellowship of the Ring", null);
		VHS lotr2 = new VHS("Two towers", null);
		VHS lotr3 = new VHS("Return of the King", null);
		VHS pulp = new VHS("Pulp Fiction", null);
		VHS django = new VHS("Django Unchained", null);
		VHS inglorious = new VHS("Inglorious Basterds", null);
		VHS dogs = new VHS("Reservoir Dogs", null);
		
		vhsRepository.save(lotr1);
		vhsRepository.save(lotr2);
		vhsRepository.save(lotr3);
		vhsRepository.save(pulp);
		vhsRepository.save(django);
		vhsRepository.save(inglorious);
		vhsRepository.save(dogs);
		
		Rental rental = new Rental();
		rental.setUser(user);
		rental.setDateFrom(new Timestamp(System.currentTimeMillis()));
		rental.setDateTo(new Timestamp(System.currentTimeMillis() + 100000000));
		rental.setExpired(false);
		rental.setVhsrented(dogs);
		
		user.getRentals().add(rental);
		dogs.setRental(rental);
		
		rentalRepository.save(rental);
		userRepository.save(user);
		vhsRepository.save(dogs);
	}

}
