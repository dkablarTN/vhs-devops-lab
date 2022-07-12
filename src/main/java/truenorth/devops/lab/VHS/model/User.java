package truenorth.devops.lab.VHS.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Korisnik")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private int id;
	
	@NotNull(message = "Username cannot be null.")
	@Column(name = "username")
	private String username;
	
	@NotNull(message = "Password cannot be null.")
	@Column(name = "password")
	private String password;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "active")
	private boolean active;
	
	@OneToMany(mappedBy = "user")
	private List<Rental> rentals;
	
	public User() {
	}

	public User(int id, String username, String password, String role, boolean active, List<Rental> rentals) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.active = active;
		this.rentals = rentals;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}
}