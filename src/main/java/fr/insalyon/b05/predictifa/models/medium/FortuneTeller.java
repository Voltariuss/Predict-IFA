package fr.insalyon.b05.predictifa.models.medium;

import fr.insalyon.b05.predictifa.models.Gender;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
public class FortuneTeller extends Medium implements Serializable {

    private static final long serialVersionUID = 1L;
    
    protected FortuneTeller() {}
    
    public FortuneTeller(String denomination, Gender gender, String presentation) {
        super(denomination, gender, presentation);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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
        final FortuneTeller other = (FortuneTeller) obj;
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
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.medium.FortuneTeller["
                + "id=" + this.id
                + ", denomination=" + this.denomination
                + ", gender=" + this.gender
                + ", presentation=" + this.presentation
                + "]";
    }
}
