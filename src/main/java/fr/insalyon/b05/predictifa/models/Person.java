package fr.insalyon.b05.predictifa.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Person implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Column(nullable = false, unique = true)
    protected String mail;
    
    @Column(nullable = false)
    protected String password;
    
    @Column(nullable = false)
    protected String firstname;
    
    @Column(nullable = false)
    protected String lastname;
    
    @Column(nullable = false)
    protected String postalAddress;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    protected Date birthDate;
    
    @Column(nullable = false)
    protected String phoneNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Gender gender;
    
    protected Person() {}
    
    public Person(String mail, String password, String firstname, String lastname, String postalAddress, Date birthDate, String phoneNumber, Gender gender) {
        this.mail = mail;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.postalAddress = postalAddress;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
    
    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.id != null ? this.id.hashCode() : 0);
        hash += (this.mail != null ? this.mail.hashCode() : 0);
        hash += (this.password != null ? this.password.hashCode() : 0);
        hash += (this.firstname != null ? this.firstname.hashCode() : 0);
        hash += (this.lastname != null ? this.lastname.hashCode() : 0);
        hash += (this.postalAddress != null ? this.postalAddress.hashCode() : 0);
        hash += (this.birthDate != null ? this.birthDate.hashCode() : 0);
        hash += (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0);
        hash += (this.gender != null ? this.gender.hashCode() : 0);
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
        final Person other = (Person) obj;
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
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.Person["
                + "id=" + this.id
                + ", mail=\"" + this.mail + "\""
                + ", password=\"" + this.password + "\""
                + ", firstname=\"" + this.firstname + "\""
                + ", lastname=\"" + this.lastname + "\""
                + ", postalAddress=\"" + this.postalAddress + "\""
                + ", birthDate=\"" + this.birthDate + "\""
                + ", phoneNumber=\"" + this.phoneNumber + "\""
                + ", gender=\"" + this.gender + "\""
                + "]";
    }
    
}
