package fr.insalyon.b05.predictifa.models.medium;

import fr.insalyon.b05.predictifa.models.Gender;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class Astrologer extends Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private String formation;
    
    @Column(nullable = false)
    private String promotion;
    
    protected Astrologer() {}
    
    public Astrologer(String denomination, Gender gender, String presentation, String formation, String promotion) {
        super(denomination, gender, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash += (formation != null ? formation.hashCode() : 0);
        hash += (promotion != null ? promotion.hashCode() : 0);
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
        final Astrologer other = (Astrologer) obj;
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
        if (!Objects.equals(this.formation, other.formation)) {
            return false;
        }
        if (!Objects.equals(this.promotion, other.promotion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.medium.Astrologer["
                + "Medium=" + super.toString()
                + ", formation=\"" + this.formation + "\""
                + ", promotion=\"" + this.promotion + "\""
                + "]";
    }
}
