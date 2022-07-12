package truenorth.devops.lab.VHS.model;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "VHS")
public class VHS {

    @Id
    @GeneratedValue
    @Column(name = "vhs_id")
    private Long id;

    @NotNull(message = "Title can not be null.")
    @Column(name = "title")
    private String title;

    @OneToOne(mappedBy = "vhsrented")
    private Rental rental;

    public VHS() {
    }

    public VHS(String title, Rental rental) {
        this.title = title;
        this.rental = rental;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VHS vhs = (VHS) o;
        return Objects.equals(id, vhs.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return title;
    }
}
