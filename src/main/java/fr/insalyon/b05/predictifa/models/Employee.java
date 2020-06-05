/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author aleryc
 */
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
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.Employee[ id=" + this.getId() + " ]";
    }
    
}
