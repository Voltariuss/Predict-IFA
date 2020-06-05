/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.services;

import fr.insalyon.b05.predictifa.astro.AstroAPI;
import fr.insalyon.b05.predictifa.dao.ConsultationDAO;
import fr.insalyon.b05.predictifa.dao.CustomerDAO;
import fr.insalyon.b05.predictifa.dao.EmployeeDAO;
import fr.insalyon.b05.predictifa.dao.JpaUtil;
import fr.insalyon.b05.predictifa.dao.MediumDAO;
import fr.insalyon.b05.predictifa.dao.PersonDAO;
import fr.insalyon.b05.predictifa.models.Consultation;
import fr.insalyon.b05.predictifa.models.Customer;
import fr.insalyon.b05.predictifa.models.Employee;
import fr.insalyon.b05.predictifa.models.Medium;
import fr.insalyon.b05.predictifa.models.Person;
import java.util.Date;
import java.util.List;
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
        // TODO : use AstroAPI to fetch profile
        
        AstroAPI astro = new AstroAPI();
        CustomerDAO customerDao = new CustomerDAO();
        List<String> profile = null;
        
        try {
            profile = astro.getProfil(customer.getFirstname(), customer.getBirthDate());
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - Astro Get Profile: " + customer, ex);
            throw new Exception("Failed Astro Get Profile");
        }
        
        customer.setZodiac(profile.get(0));
        customer.setChineseAstro(profile.get(1));
        customer.setLuckyColor(profile.get(2));
        customer.setTotem(profile.get(3));
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            customerDao.create(customer);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "Success - Customer registration: " + customer);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - Customer registration: " + customer, ex);
            JpaUtil.annulerTransaction();
            throw new Exception("Failed registration customer");
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
    // Medium service
    // ----------------------------------
    public void registerMedium(Medium medium) throws Exception {
        MediumDAO mediumDao = new MediumDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            mediumDao.insert(medium);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.FINE, "Success - Medium registration: {0}", medium);
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - Medium registration: " + medium, ex);
            throw ex;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
    
    public Medium getMediumById(long id) {
        MediumDAO mediumDao = new MediumDAO();
        
        JpaUtil.creerContextePersistance();
        Medium medium = mediumDao.find(id);
        JpaUtil.fermerContextePersistance();
        
        Logger.getAnonymousLogger().log(Level.INFO, "Success - Get medium by id: " + medium);
        
        return medium;
    }
    
    public List<Medium> getAllMediums() {
        MediumDAO mediumDao = new MediumDAO();
        
        JpaUtil.creerContextePersistance();
        List<Medium> mediums = mediumDao.findAll();
        JpaUtil.fermerContextePersistance();
        
        Logger.getAnonymousLogger().log(Level.INFO, "Success - Get all mediums: " + mediums);
        
        return mediums;
    }
    
    // ----------------------------------
    // Consultation service
    // ----------------------------------
    public Consultation initConsultation(long idCustomer, long idMedium) throws Exception {
        CustomerDAO customerDao = new CustomerDAO();
        MediumDAO mediumDao = new MediumDAO();
        EmployeeDAO employeeDao = new EmployeeDAO();
        ConsultationDAO consultationDao = new ConsultationDAO();
        
        JpaUtil.creerContextePersistance();
        
        Customer customer = customerDao.getById(idCustomer);
        if (customer == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - initConsultation: Customer not found");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Customer not found");
        }
        
        Medium medium = mediumDao.find(idMedium);
        if (medium == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - initConsultation: Medium not found");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Medium not found");
        }
        
        // Vérifier que le client n'a pas de consultation en cours
        Consultation consultation = consultationDao.getCustomerCurrentConsultation(customer);
        if (consultation != null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - initConsultation: Customer already in consultation");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Customer already in consultation");
        }
        
        // Trouver un employé disponible pour la consultation
        Employee employee = employeeDao.findOneAvailable(medium);
        if (employee == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - initConsultation: There is no available employee");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("There is no available employee");
        }
        
        // Créer la consultation
        Consultation newConsultation = new Consultation(employee, customer, medium);
        
        try {
            JpaUtil.ouvrirTransaction();
            consultationDao.create(newConsultation);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "Success - initConsultation");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - initConsultation: error creation", ex);
            JpaUtil.annulerTransaction();
            throw new Exception("Error creation");
        } finally {
            JpaUtil.fermerContextePersistance();
        }
       
        return newConsultation;
    }
    
    public void startConsultation(long idConsultation) throws Exception {
        ConsultationDAO consultationDao = new ConsultationDAO();
        
        JpaUtil.creerContextePersistance();
        
        Consultation consultation = consultationDao.getById(idConsultation);
        if (consultation == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - startConsultation: Consultation not found");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Consultation not found");
        }
        
        if (consultation.getStartDate() != null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - startConsultation: Consultation already started");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Consultation already started");
        }
        
        consultation.setStartDate(new Date());
        
        try {
            JpaUtil.ouvrirTransaction();
            consultationDao.update(consultation);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "Success - startConsultation");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - startConsultation: error update", ex);
            JpaUtil.annulerTransaction();
            throw new Exception("Error update");
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
    }
    
    public void endConsultation(long idConsultation)throws Exception {
        ConsultationDAO consultationDao = new ConsultationDAO();
        
        JpaUtil.creerContextePersistance();
        
        Consultation consultation = consultationDao.getById(idConsultation);
        if (consultation == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - endConsultation: Consultation not found");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Consultation not found");
        }
        
        if (consultation.getEndDate() != null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - endConsultation: Consultation already ended");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Consultation already ended");
        }
        
        consultation.setEndDate(new Date());
        
        try {
            JpaUtil.ouvrirTransaction();
            consultationDao.update(consultation);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "Success - endConsultation");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - endConsultation: error update", ex);
            JpaUtil.annulerTransaction();
            throw new Exception("Error update");
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
    }
    
    public Consultation getCustomerCurrentConsultation(long idCustomer) throws Exception {
        ConsultationDAO consultationDao = new ConsultationDAO();
        CustomerDAO customerDao = new CustomerDAO();
        
        JpaUtil.creerContextePersistance();
        
        Customer customer = customerDao.getById(idCustomer);
        if (customer == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - getCustomerCurrentConsultation: Customer not found");
            JpaUtil.fermerContextePersistance();
            throw new Exception("Customer not found");
        }
        Consultation consultation = consultationDao.getCustomerCurrentConsultation(customer);
        Logger.getAnonymousLogger().log(Level.INFO, "Success - getCustomerCurrentConsultation: " + consultation);

        JpaUtil.fermerContextePersistance();
        return consultation;
    }
    
    public List<Consultation> getCustomerConsultations(long idCustomer) throws Exception {
        ConsultationDAO consultationDao = new ConsultationDAO();
        CustomerDAO customerDao = new CustomerDAO();
        
        JpaUtil.creerContextePersistance();
        Customer customer = customerDao.getById(idCustomer);
        if (customer == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - getCustomerConsultations: Customer not found");
            JpaUtil.fermerContextePersistance();
            throw new Exception("Customer not found");
        }
        
        List<Consultation> consultations = consultationDao.getCustomerConsultations(customer);
        Logger.getAnonymousLogger().log(Level.INFO, "Success - getCustomerConsultations: " + consultations.size());

        JpaUtil.fermerContextePersistance();
        return consultations;
    }
    
    public Consultation getEmployeeCurrentConsultation(long idEmployee) throws Exception {
        ConsultationDAO consultationDao = new ConsultationDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();
        
        JpaUtil.creerContextePersistance();
        Employee employee = employeeDAO.getById(idEmployee);
        if (employee == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - getEmployeeCurrentConsultation: Employee not found");
            JpaUtil.fermerContextePersistance();
            throw new Exception("Employee not found");
        }
        Consultation consultation = consultationDao.getEmployeeCurrentConsultation(employee);
        Logger.getAnonymousLogger().log(Level.INFO, "Success - getEmployeeCurrentConsultation: " + consultation);

        JpaUtil.fermerContextePersistance();
        return consultation;
    }
    
    public List<Consultation> getEmployeeConsultations(long idEmployee) throws Exception {
        ConsultationDAO consultationDao = new ConsultationDAO();
        EmployeeDAO employeeDao = new EmployeeDAO();
        
        JpaUtil.creerContextePersistance();
        Employee employee = employeeDao.getById(idEmployee);
        if (employee == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - getEmployeeConsultations: Employee not found");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Employee not found");
        }
        
        List<Consultation> consultations = consultationDao.getEmployeeConsultations(employee);
        Logger.getAnonymousLogger().log(Level.INFO, "Success - getEmployeeConsultations: " + consultations.size());

        JpaUtil.fermerContextePersistance();
        return consultations;
    }
}
