/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.Consultation;
import fr.insalyon.b05.predictifa.models.Customer;
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

    public Consultation getCurrentConsultation(Customer customer) {
        String query = "select c from Consultation c"
                + " where c.customer = :customer"
                + " and c.endDate is null";
        TypedQuery<Consultation> consultation = JpaUtil.obtenirContextePersistance()
                .createNamedQuery(query, Consultation.class);
        
        consultation.setParameter("customer", customer);
        
        List<Consultation> result = consultation.getResultList();
        
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }
}
