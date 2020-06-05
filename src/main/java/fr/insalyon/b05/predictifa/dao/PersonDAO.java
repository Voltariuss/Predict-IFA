/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.Person;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author aleryc
 */
public class PersonDAO {
    public Person getByLogin(String login, String password) {
        TypedQuery<Person> query = JpaUtil.obtenirContextePersistance()
                .createQuery("select p from Person p where p.mail = :mail and p.password = :password", Person.class);
        
        query.setParameter("mail", login);
        query.setParameter("password", password);
        
        List<Person> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }
}
