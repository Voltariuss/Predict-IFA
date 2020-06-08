package fr.insalyon.b05.predictifa.models;

import fr.insalyon.b05.predictifa.models.medium.Medium;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date requestDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    private String commentary;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Employee employee;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Medium medium;
    
    protected Consultation() {}
    
    public Consultation(Employee employee, Customer customer, Medium medium) {
        this.requestDate = new Date();
        this.setEmployee(employee);
        this.setCustomer(customer);
        this.setMedium(medium);
    }
    
    public Long getId() {
        return id;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        if (this.employee != null) {
            this.employee.removeConsultation(this);
        }
        this.employee = employee;
        if (this.employee != null) {
            this.employee.addConsultation(this);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        if (this.customer != null) {
            this.customer.removeConsultation(this);
        }
        this.customer = customer;
        if (this.customer != null) {
            this.customer.addConsultation(this);
        }
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        if (this.medium != null) {
            this.medium.removeConsultation(this);
        }
        this.medium = medium;
        if (this.medium != null) {
            this.medium.addConsultation(this);
        }
        
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.id != null ? this.id.hashCode() : 0);
        hash += (this.requestDate != null ? this.requestDate.hashCode() : 0);
        hash += (this.startDate != null ? this.startDate.hashCode() : 0);
        hash += (this.endDate != null ? this.endDate.hashCode() : 0);
        hash += (this.commentary != null ? this.commentary.hashCode() : 0);
        hash += (this.employee != null ? this.employee.hashCode() : 0);
        hash += (this.customer != null ? this.customer.hashCode() : 0);
        hash += (this.medium != null ? this.medium.hashCode() : 0);
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
        final Consultation other = (Consultation) obj;
        if (!Objects.equals(this.commentary, other.commentary)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.requestDate, other.requestDate)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (!Objects.equals(this.endDate, other.endDate)) {
            return false;
        }
        if (!Objects.equals(this.employee, other.employee)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.medium, other.medium)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.Consultation["
                + "id=" + this.id
                + ", requestDate=\"" + this.requestDate + "\""
                + ", startDate=\"" + this.startDate + "\""
                + ", endDate=\"" + this.endDate + "\""
                + ", commentary=\"" + this.commentary + "\""
                + ", employee=" + this.employee
                + ", customer=" + this.customer
                + ", medium=" + this.medium
                + "]";
    } 
}
