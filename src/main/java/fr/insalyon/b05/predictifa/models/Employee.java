package fr.insalyon.b05.predictifa.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("E")
public class Employee extends Person {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private String proPhoneNumber;
    
    @OneToMany(mappedBy = "employee")
    private List<Consultation> consultations;
    
    protected Employee() {}

    public Employee(String proPhoneNumber, String mail, String password, String firstname, String lastname, String postalAddress, Date birthDate, String phoneNumber, Gender genre) {
        super(mail, password, firstname, lastname, postalAddress, birthDate, phoneNumber, genre);
        this.proPhoneNumber = proPhoneNumber;
    }
    
    public String getProPhoneNumber() {
        return proPhoneNumber;
    }

    public void setProPhoneNumber(String proPhoneNumber) {
        this.proPhoneNumber = proPhoneNumber;
    }
    
    public List<Consultation> getConsultations() {
        return consultations;
    }
    
    public void addConsultation(Consultation consultation) {
        this.consultations.add(consultation);
        consultation.setEmployee(this);
    }
    
    public void removeConsultation(Consultation consultation) {
        this.consultations.remove(consultation);
        consultation.setEmployee(null);
    }
    
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash += (this.proPhoneNumber != null ? this.proPhoneNumber.hashCode() : 0);
        hash += (this.consultations != null ? this.consultations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }
        if (!Objects.equals(this.postalAddress, other.postalAddress)) {
            return false;
        }
        if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.birthDate, other.birthDate)) {
            return false;
        }
        if (this.gender != other.gender) {
            return false;
        }
        if (!Objects.equals(this.proPhoneNumber, other.proPhoneNumber)) {
            return false;
        }
        if (!Objects.equals(this.consultations, other.consultations)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.Employee["
                + "Person=" + super.toString()
                + ", proPhoneNumber=\"" + this.proPhoneNumber + "\""
                + ", consultations=" + this.consultations
                + "]";
    }
}
