package truenorth.devops.lab.VHS.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import truenorth.devops.lab.VHS.model.Rental;
import truenorth.devops.lab.VHS.model.User;
import truenorth.devops.lab.VHS.model.VHS;
import truenorth.devops.lab.VHS.service.RentalService;
import truenorth.devops.lab.VHS.service.UserService;
import truenorth.devops.lab.VHS.service.VHSService;
import truenorth.devops.lab.VHS.util.RentalDurationUtil;

@Controller
public class RentalController {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RentalController.class);
	
	@Autowired
	private RentalService rentalService;
	
	@Autowired
	private VHSService vhsService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/api/rental")
	public ModelAndView rental(Model model) {
		log.info("GET Request for the list of rentals.");
		
		User user = userService.getUserByUsername(getUsername());
		List<Rental> rentals;
		
		if(user.getRole().equals("ROLE_ADMIN"))
			rentals = rentalService.getAllRentals();
		else
			rentals = rentalService.getRentalsByUserId(user.getId());
		
		for(Rental r : rentals) {
			if(RentalDurationUtil.rentExpired(r)) {
				r.setExpired(true);
				r.setLateFee(new BigDecimal(50));
				rentalService.addRental(r);
			}
		}
		
		model.addAttribute("rentals", rentals);
		model.addAttribute("username", getUsername());
		
		return new ModelAndView("rental");
	}
	
	@GetMapping("/api/vhs/new")
	public ModelAndView addVHS(Model model) {
		log.info("GET Request for the form for adding VHS tapes.");
		
		long noOfVHS = vhsService.getNoOfVHS();
		model.addAttribute("noOfVHS", noOfVHS + 1);
		
		return new ModelAndView("VHSForm");
	}
	
	@PostMapping("/api/vhs/new")
	public ModelAndView saveVHS(@RequestParam String title, Model model) {
		if(title == null || title.isBlank()) {
			log.info("VHS tape cannot be inserted into database. Illegal arguments.");
			
			model.addAttribute("error", "Title cannot be empty.");
			return this.addVHS(model);
		}
		
		VHS vhs = new VHS();
		vhs.setTitle(title);
		
		log.info("VHS tape: " + vhs.toString() + " is inserted into database.");
		
		vhsService.addVHS(vhs);
		
		model.addAttribute("message", "VHS tape with title " + title + " added.");
		
		return new ModelAndView("entryAdded");
	}

	private String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return username;
	}
}
