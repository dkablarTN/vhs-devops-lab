package truenorth.devops.lab.VHS.service;

import java.util.List;

import org.springframework.stereotype.Service;

import truenorth.devops.lab.VHS.model.Rental;
import truenorth.devops.lab.VHS.repository.RentalRepository;

@Service
public class RentalServiceImpl implements RentalService {

	private final RentalRepository iRentalRepository;
	
	public RentalServiceImpl(RentalRepository rentalRepository) {
		this.iRentalRepository = rentalRepository;
	}
	
	@Override
	public List<Rental> getAllRentals() {
		return iRentalRepository.getRentals();
	}

	@Override
	public void addRental(Rental rental) {
		iRentalRepository.save(rental);
	}

	@Override
	public void deleteRental(Rental rental) {
		iRentalRepository.delete(rental);
	}

	@Override
	public Rental getRentalById(long id) {
		return iRentalRepository.getRentalById(id);
	}

	@Override
	public List<Rental> getRentalsByUserId(long id) {
		return iRentalRepository.getRentalsByUserId(id);
	}
}
