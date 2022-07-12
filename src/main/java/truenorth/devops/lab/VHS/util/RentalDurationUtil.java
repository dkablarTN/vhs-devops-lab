package truenorth.devops.lab.VHS.util;

import java.sql.Timestamp;

import truenorth.devops.lab.VHS.model.Rental;

public class RentalDurationUtil {
	
	public static boolean rentExpired(Rental rental) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		if(rental.getDateTo().compareTo(ts) <= 0)
			return true;
		return false;
	}
}
