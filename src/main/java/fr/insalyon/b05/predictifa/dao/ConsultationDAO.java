/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.Consultation;
import fr.insalyon.b05.predictifa.models.Customer;
import fr.insalyon.b05.predictifa.models.Employee;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author aleryc
 */
public class ConsultationDAO {
    public void create(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }
    
    public Consultation getById(long id) {
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    }
    
    public void update(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().merge(consultation);
    }

    public Consultation getCustomerCurrentConsultation(Customer customer) {
        String query = "select c from Consultation c"
                + " where c.customer = :customer"
                + " and c.endDate is null";
        TypedQuery<Consultation> consultation = JpaUtil.obtenirContextePersistance()
                .createQuery(query, Consultation.class);
        
        consultation.setParameter("customer", customer);
        
        List<Consultation> result = consultation.getResultList();
        
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public List<Consultation> getCustomerConsultations(Customer customer) {
        String query = "select c from Consultation c"
                + " where c.customer = :customer"
                + " order by c.requestDate";
        
        TypedQuery<Consultation> consultation = JpaUtil.obtenirContextePersistance()
                .createQuery(query, Consultation.class);
        
        consultation.setParameter("customer", customer);
        
        return consultation.getResultList();
    }
    
    public Consultation getEmployeeCurrentConsultation(Employee employee) {
        String query = "select c from Consultation c"
                + " where c.employee = :employee"
                + " and c.endDate is null";
        TypedQuery<Consultation> consultation = JpaUtil.obtenirContextePersistance()
                .createQuery(query, Consultation.class);
        
        consultation.setParameter("employee", employee);
        
        List<Consultation> result = consultation.getResultList();
        
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public List<Consultation> getEmployeeConsultations(Employee employee) {
        String query = "select c from Consultation c"
                + " where c.employee = :employee"
                + " order by c.requestDate";
        
        TypedQuery<Consultation> consultation = JpaUtil.obtenirContextePersistance()
                .createQuery(query, Consultation.class);
        
        consultation.setParameter("employee", employee);
        
        return consultation.getResultList();
    }
}
