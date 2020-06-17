package fr.insalyon.b05.predictifa.models.medium;

import fr.insalyon.b05.predictifa.models.Consultation;
import fr.insalyon.b05.predictifa.models.Gender;
import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Column(nullable = false)
    protected String denomination;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Gender gender;
    
    @Column(nullable = false)
    protected String presentation;
    
    @OneToMany(mappedBy = "medium")
    protected List<Consultation> consultations;
    
    protected Medium() {}
    
    protected Medium(String denomination, Gender gender, String presentation) {
        this.denomination = denomination;
        this.gender = gender;
        this.presentation = presentation;
    }

    public Long getId() {
        return id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }
    
    public List<Consultation> getConsultations() {
        return consultations;
    }
    
    public void addConsultation(Consultation consultation) {
        this.consultations.add(consultation);
    }
    
    public void removeConsultation(Consultation consultation) {
        this.consultations.remove(consultation);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (denomination != null ? denomination.hashCode() : 0);
        hash += (gender != null ? gender.hashCode() : 0);
        hash += (presentation != null ? presentation.hashCode() : 0);
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
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Medium other = (Medium) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.denomination, other.denomination)) {
            return false;
        }
        if (!Objects.equals(this.gender, other.gender)) {
            return false;
        }
        if (!Objects.equals(this.presentation, other.presentation)) {
            return false;
        }
        if (!Objects.equals(this.consultations, other.consultations)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.medium.Medium["
                + "id=" + this.id
                + ", denomination=\"" + this.denomination + "\""
                + ", gender=\"" + this.gender + "\""
                + ", presentation=\"" + this.presentation + "\""
                + ", consultations=" + this.consultations
                + "]";
    }
}
