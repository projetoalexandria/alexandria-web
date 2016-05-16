package br.com.psi.alexandria.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PublishingHouse.
 */
@Entity
@Table(name = "publishing_house")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PublishingHouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nationality")
    private String nationality;

    @NotNull
    @Column(name = "cnpj", nullable = false)
    private Integer cnpj;

    @Column(name = "site")
    private String site;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "additional_information")
    private String additionalInformation;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "address_number", nullable = false)
    private Integer addressNumber;

    @NotNull
    @Column(name = "phone", nullable = false)
    private Integer phone;

    @ManyToOne
    private Cep cep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getCnpj() {
        return cnpj;
    }

    public void setCnpj(Integer cnpj) {
        this.cnpj = cnpj;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(Integer addressNumber) {
        this.addressNumber = addressNumber;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Cep getCep() {
        return cep;
    }

    public void setCep(Cep cep) {
        this.cep = cep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PublishingHouse publishingHouse = (PublishingHouse) o;
        if(publishingHouse.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, publishingHouse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PublishingHouse{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", nationality='" + nationality + "'" +
            ", cnpj='" + cnpj + "'" +
            ", site='" + site + "'" +
            ", email='" + email + "'" +
            ", additionalInformation='" + additionalInformation + "'" +
            ", active='" + active + "'" +
            ", addressNumber='" + addressNumber + "'" +
            ", phone='" + phone + "'" +
            '}';
    }
}
