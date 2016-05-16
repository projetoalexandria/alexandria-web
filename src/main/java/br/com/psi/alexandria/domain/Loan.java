package br.com.psi.alexandria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Loan.
 */
@Entity
@Table(name = "loan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @NotNull
    @Column(name = "devolution_date", nullable = false)
    private LocalDate devolutionDate;

    @NotNull
    @Column(name = "max_devolution_date", nullable = false)
    private LocalDate maxDevolutionDate;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDevolutionDate() {
        return devolutionDate;
    }

    public void setDevolutionDate(LocalDate devolutionDate) {
        this.devolutionDate = devolutionDate;
    }

    public LocalDate getMaxDevolutionDate() {
        return maxDevolutionDate;
    }

    public void setMaxDevolutionDate(LocalDate maxDevolutionDate) {
        this.maxDevolutionDate = maxDevolutionDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Loan loan = (Loan) o;
        if(loan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Loan{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", loanDate='" + loanDate + "'" +
            ", devolutionDate='" + devolutionDate + "'" +
            ", maxDevolutionDate='" + maxDevolutionDate + "'" +
            '}';
    }
}
