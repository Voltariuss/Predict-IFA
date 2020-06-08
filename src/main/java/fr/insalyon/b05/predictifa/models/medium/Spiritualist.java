package fr.insalyon.b05.predictifa.models.medium;

import fr.insalyon.b05.predictifa.models.Gender;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("S")
public class Spiritualist extends Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(nullable = false)
    private String support;
    
    protected Spiritualist() {}
    
    public Spiritualist(String denomination, Gender gender, String presentation, String support) {
        super(denomination, gender, presentation);
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash += (support != null ? support.hashCode() : 0);
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
        final Spiritualist other = (Spiritualist) obj;
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
        if (!Objects.equals(this.support, other.support)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.medium.Spiritualist["
                + "Medium=" + super.toString()
                + ", support=\"" + this.support + "\""
                + "]";
    }
    
}
