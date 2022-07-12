package truenorth.devops.lab.VHS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VhsRentalApplication {

private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(VhsRentalApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(VhsRentalApplication.class, args);
		
		log.info("Application initialized.");
	}

}
