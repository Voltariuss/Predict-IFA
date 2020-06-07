/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.Employee;
import fr.insalyon.b05.predictifa.models.Medium;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author aleryc
 */
public class EmployeeDAO {
    
    public void insert(Employee employee) {
        JpaUtil.obtenirContextePersistance().persist(employee);
    }
    
    public void update(Employee employee) {
        JpaUtil.obtenirContextePersistance().merge(employee);
    }
    
    public void delete(Employee employee) {
        JpaUtil.obtenirContextePersistance().remove(employee);
    }
    
    public Employee find(long id) {
        return JpaUtil.obtenirContextePersistance().find(Employee.class, id);
    }

    public Employee findOneAvailable(Medium medium) {
        String jpql = "select e, count(c) from Employee e left join e.consultations c"
                + " where e not in (select e from Consultation c join c.employee e where c.endDate is null)"
                + " and e.gender = :gender"
                + " group by e"
                + " order by count(c)";
        
        Query query = JpaUtil.obtenirContextePersistance()
                .createQuery(jpql);
        
        query.setParameter("gender", medium.getGender());
        query.setMaxResults(1);
        
        List<Object[]> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return (Employee) result.get(0)[0];
        }
    }
}
