package truenorth.devops.lab.VHS.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Rental")
public class Rental {

    @Id
    @GeneratedValue
    @Column(name = "rental_id")
    private Long id;
    
    @NotNull(message = "Date from cannot be null.")
    @Column(name = "date_from")
    private Timestamp dateFrom;
    
    @NotNull(message = "Date to cannot be null.")
    @Column(name = "date_to")
    private Timestamp dateTo;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToOne
    @JoinColumn(name = "vhs_id")
    private VHS vhsrented;
    
    @NotNull(message = "Expired cannot be null.")
    @Column(name = "expired")
    private boolean expired;
    
    @Column(name = "lateFee")
    private BigDecimal lateFee;
    
    public Rental() {
    }

	public Rental(@NotNull(message = "Date from cannot be null.") Timestamp dateFrom,
			@NotNull(message = "Date to cannot be null.") Timestamp dateTo, User user, VHS vhsrented,
			@NotNull(message = "Expired cannot be null.") boolean expired, BigDecimal lateFee) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.user = user;
		this.vhsrented = vhsrented;
		this.expired = expired;
		this.lateFee = lateFee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Timestamp getDateTo() {
		return dateTo;
	}

	public void setDateTo(Timestamp dateTo) {
		this.dateTo = dateTo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public VHS getVhsrented() {
		return vhsrented;
	}

	public void setVhsrented(VHS vhsrented) {
		this.vhsrented = vhsrented;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public BigDecimal getLateFee() {
		return lateFee;
	}

	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rental other = (Rental) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Rental [id=" + id + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", user=" + user + ", vhsrented="
				+ vhsrented + ", expired=" + expired + ", lateFee=" + lateFee + "]";
	}
}
