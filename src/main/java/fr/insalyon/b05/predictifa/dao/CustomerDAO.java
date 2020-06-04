/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.Customer;

/**
 *
 * @author aleryc
 */
public class CustomerDAO {
    public void create(Customer customer) {
        JpaUtil.obtenirContextePersistance().persist(customer);
    }
    
    public Customer getById(long id) {
        return JpaUtil.obtenirContextePersistance().find(Customer.class, id);
    }
}
