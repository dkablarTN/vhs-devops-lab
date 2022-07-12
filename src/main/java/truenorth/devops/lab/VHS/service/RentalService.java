package truenorth.devops.lab.VHS.service;

import java.util.List;

import truenorth.devops.lab.VHS.model.Rental;

public interface RentalService {
	
	public List<Rental> getAllRentals();
	
	public Rental getRentalById(long id);
	
	public void addRental(Rental rental);
	
	public void deleteRental(Rental rental);
	
	public List<Rental> getRentalsByUserId(long id);
}
