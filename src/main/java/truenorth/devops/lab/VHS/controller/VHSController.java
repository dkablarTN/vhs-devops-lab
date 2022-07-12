package truenorth.devops.lab.VHS.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import truenorth.devops.lab.VHS.model.Rental;
import truenorth.devops.lab.VHS.model.User;
import truenorth.devops.lab.VHS.model.VHS;
import truenorth.devops.lab.VHS.service.RentalService;
import truenorth.devops.lab.VHS.service.UserService;
import truenorth.devops.lab.VHS.service.VHSService;

@Controller
public class VHSController {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(VHSController.class);
	
	@Autowired
	private RentalService rentalService;
	
	@Autowired
	private VHSService vhsService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/vhs")
	public ModelAndView vhs(Model model) {
		log.info("POST Request for the list of VHS tapes.");
		
		List<VHS> vhs = vhsService.getAllVHS();
		model.addAttribute("vhs", vhs);
		model.addAttribute("username", getUsername());
		
		User user = userService.getUserByUsername(getUsername());
		model.addAttribute("isAdmin", user.getRole().equals("ROLE_ADMIN"));
		
		return new ModelAndView("vhs");
	}
	
	@GetMapping("/api/vhs/rent/{id}")
	public ModelAndView rent(@PathVariable long id, Model model) {
		VHS vhs = vhsService.getVHSById(id);
		
		if(vhs == null) {
			log.info("GET request to rent the non-existing VHS tape.");
			model.addAttribute("error", "VHS does not exist.");
			
			List<VHS> vhsList = vhsService.getAllVHS();
			model.addAttribute("vhs", vhsList);
			model.addAttribute("username",getUsername());
			
			return new ModelAndView("vhs");
		}
		
		log.info("GET request to rent the \"" + vhs.getTitle() + "\" VHS tape.");
		model.addAttribute("vhsid", vhs.getId());
		return new ModelAndView("RentalForm");
	}
	
	@PostMapping("/api/vhs/rent/new")
	public ModelAndView rent(@RequestParam long vhsid, @RequestParam String returnDate, Model model) {
		if(Timestamp.valueOf(returnDate + " 12:00:00").compareTo(new Timestamp(System.currentTimeMillis())) <= 0) {
			model.addAttribute("error", "The return date must be bigger than the rental date.");
			model.addAttribute("vhsid", vhsid);
			
			log.info("Mistake while filling the rental form.");
			return new ModelAndView("RentalForm");
		}
		
		User user = userService.getUserByUsername(getUsername());
		VHS vhs = vhsService.getVHSById(vhsid);
		
		Rental rental = new Rental();
		rental.setUser(user);
		rental.setVhsrented(vhs);
		rental.setDateFrom(new Timestamp(System.currentTimeMillis()));
		rental.setDateTo(Timestamp.valueOf(returnDate + " 12:00:00"));
		rental.setExpired(false);
		rentalService.addRental(rental);
		
		model.addAttribute("message", "Rent is completed. You have to return it before " + rental.getDateTo());
		
		log.info("Rent is completed.");
		return new ModelAndView("entryAdded");
	}
	
	@GetMapping("/api/vhs/return/{id}")
	public ModelAndView returnVHS(@PathVariable int id, Model model) {
		
		Rental rental = rentalService.getRentalById(id);
		rentalService.deleteRental(rental);
		
		if(!rental.isExpired()) {
			model.addAttribute("message", "VHS tape " + rental.getVhsrented().getTitle() + " returned. See you next time :)");
			log.info("VHS tape returned in time.");
		} else {
			model.addAttribute("message", "Late fee of " + rental.getLateFee() + "payed and VHS tape " + rental.getVhsrented().getTitle() + " returned. See you next time :)");
			log.info("Late eee is paid, and the VHS tape returned.");
		}
		
		return new ModelAndView("entryAdded");
	}

	private String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return username;
	}
}