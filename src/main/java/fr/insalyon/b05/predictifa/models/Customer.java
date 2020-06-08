package fr.insalyon.b05.predictifa.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("C")
public class Customer extends Person {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private String zodiac;
    
    @Column(nullable = false)
    private String chineseAstro;
    
    @Column(nullable = false)
    private String luckyColor;
    
    @Column(nullable = false)
    private String totem;
    
    @OneToMany(mappedBy = "employee")
    private List<Consultation> consultations;
    
    protected Customer() {}
    
    public Customer(String mail, String password, String firstname, String lastname, String postalAddress, Date birthDate, String phoneNumber, Gender genre) {
        super(mail, password, firstname, lastname, postalAddress, birthDate, phoneNumber, genre);
    }
       
    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getChineseAstro() {
        return chineseAstro;
    }

    public void setChineseAstro(String chineseAstro) {
        this.chineseAstro = chineseAstro;
    }

    public String getLuckyColor() {
        return luckyColor;
    }

    public void setLuckyColor(String luckyColor) {
        this.luckyColor = luckyColor;
    }

    public String getTotem() {
        return totem;
    }

    public void setTotem(String totem) {
        this.totem = totem;
    }
    
    public List<Consultation> getConsultations() {
        return consultations;
    }
    
    public void addConsultation(Consultation consultation) {
        this.consultations.add(consultation);
        consultation.setCustomer(this);
    }
    
    public void removeConsultation(Consultation consultation) {
        this.consultations.remove(consultation);
        consultation.setCustomer(null);
    }
    
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash += (this.zodiac != null ? this.zodiac.hashCode() : 0);
        hash += (this.chineseAstro != null ? this.chineseAstro.hashCode() : 0);
        hash += (this.luckyColor != null ? this.luckyColor.hashCode() : 0);
        hash += (this.totem != null ? this.totem.hashCode() : 0);
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
        final Customer other = (Customer) obj;
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
        if (!Objects.equals(this.zodiac, other.zodiac)) {
            return false;
        }
        if (!Objects.equals(this.chineseAstro, other.chineseAstro)) {
            return false;
        }
        if (!Objects.equals(this.luckyColor, other.luckyColor)) {
            return false;
        }
        if (!Objects.equals(this.totem, other.totem)) {
            return false;
        }
        if (!Objects.equals(this.consultations, other.consultations)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.Client["
                + "Person=" + super.toString()
                + ", zodiac=\"" + this.zodiac + "\""
                + ", chineseAstro=\"" + this.chineseAstro + "\""
                + ", luckyColor=\"" + this.luckyColor + "\""
                + ", totem=\"" + this.totem + "\""
                + ", consultations=" + this.consultations
                + "]";
    }
    
}
