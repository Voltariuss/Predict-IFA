/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.services;

import fr.insalyon.b05.predictifa.dao.ConsultationDAO;
import fr.insalyon.b05.predictifa.dao.CustomerDAO;
import fr.insalyon.b05.predictifa.dao.EmployeeDAO;
import fr.insalyon.b05.predictifa.dao.JpaUtil;
import fr.insalyon.b05.predictifa.dao.PersonDAO;
import fr.insalyon.b05.predictifa.models.Consultation;
import fr.insalyon.b05.predictifa.models.Customer;
import fr.insalyon.b05.predictifa.models.Employee;
import fr.insalyon.b05.predictifa.models.Person;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleryc
 */
public class Service {
    
    // ----------------------------------
    // User service
    // ----------------------------------
    Person authenticate(String login, String password) {
        PersonDAO personDao = new PersonDAO();
        
        JpaUtil.creerContextePersistance();
        Person person = personDao.getByLogin(login, password);
        JpaUtil.fermerContextePersistance();
        
        Logger.getAnonymousLogger().log(Level.INFO, "Success - authenticate: " + personDao);
        
        return person;
    }
    
    // ----------------------------------
    // Customer service
    // ----------------------------------
    
    public void registerCustomer(Customer customer) throws Exception {
        CustomerDAO customerDao = new CustomerDAO();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            customerDao.create(customer);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "Success - Customer registration: " + customer);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - Customer registration: " + customer, ex);
            JpaUtil.annulerTransaction();
            throw ex;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
    
    public Customer findCustomerById(long id) {
        CustomerDAO customerDao = new CustomerDAO();
       
        JpaUtil.creerContextePersistance();
        Customer customer = customerDao.getById(id);
        JpaUtil.fermerContextePersistance();
        
        Logger.getAnonymousLogger().log(Level.INFO, "Success - Get customer by id: " + customer);
        
        return customer;
    }

    // ----------------------------------
    // Employee service
    // ----------------------------------
    public Employee findEmployeeById(long id) {
        EmployeeDAO employeeDao = new EmployeeDAO();
       
        JpaUtil.creerContextePersistance();
        Employee employee = employeeDao.getById(id);
        JpaUtil.fermerContextePersistance();
        
        Logger.getAnonymousLogger().log(Level.INFO, "Success - Get employee by id: " + employee);
        
        return employee;
    }
    
    // ----------------------------------
    // Consultation service
    // ----------------------------------
    public Consultation getCustomerCurrentConsultation(long idCustomer) throws Exception {
        ConsultationDAO consultationDao = new ConsultationDAO();
        CustomerDAO customerDao = new CustomerDAO();
        
        JpaUtil.creerContextePersistance();
        Customer customer = customerDao.getById(idCustomer);
        if (customer == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - getCurrentConsultation: Customer not found");
            throw new Exception("Customer not found");
        }
        Consultation consultation = consultationDao.getCurrentConsultation(customer);
        Logger.getAnonymousLogger().log(Level.INFO, "Success - getCurrentConsultation: " + consultation);

        JpaUtil.fermerContextePersistance();
        return consultation;
    }
    
}
