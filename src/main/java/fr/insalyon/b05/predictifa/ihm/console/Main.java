/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.ihm.console;

import fr.insalyon.b05.predictifa.dao.JpaUtil;
import fr.insalyon.b05.predictifa.models.Consultation;
import fr.insalyon.b05.predictifa.models.Customer;
import fr.insalyon.b05.predictifa.models.Employee;
import fr.insalyon.b05.predictifa.models.Person;
import fr.insalyon.b05.predictifa.services.Service;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author aleryc
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JpaUtil.init();
        
        testRegistrationClient();
        //testFindOneClientById();
        //testFindOneEmployeeById();
        //testFindEmployeeCurrentConsultation();
        //testFindEmployeeConsultations();
        JpaUtil.destroy();
    }
    
    public static void testFindCustomerCurrentConsultation() throws Exception {
        Service service = new Service();
        Consultation consultation = service.getCustomerCurrentConsultation(1L);
        System.out.println(consultation); 
    }
    
    public static void testFindEmployeeCurrentConsultation() throws Exception {
        Service service = new Service();
        Consultation consultation2 = service.getEmployeeCurrentConsultation(2L);
        System.out.println(consultation2);
        
        Consultation consultation3 = service.getEmployeeCurrentConsultation(3L);
        System.out.println(consultation3);
    }
    
    public static void testFindEmployeeConsultations() throws Exception {
        Service service = new Service();
        List<Consultation> consultation2 = service.getEmployeeConsultations(2L);
        System.out.println(consultation2.size());
        
        List<Consultation> consultation3 = service.getEmployeeConsultations(3L);
        System.out.println(consultation3.size());
    }
    
    public static void testRegistrationClient() throws Exception {
        Service service = new Service();
        Customer newClient = new Customer(
            "Balance", 
            "Tigre", 
            "Bleu", 
            "Chat", 
            "alerycserrania@gmail.com", 
            "toto", 
            "Aleryc", 
            "Serrania", 
            "11 rue des élites 69100 Villeurbanne", 
            new GregorianCalendar(1997, Calendar.FEBRUARY, 11).getTime(), 
            "0712486521", 
            Person.Gender.M
        );
        
        service.registerCustomer(newClient);
    }
    
    public static void testFindOneClientById() {
        Service service = new Service();
        Customer c = service.findCustomerById(1L);
        System.out.println(c.getId() + ": " + c.getFirstname() + " " + c.getLastname());
    }
    
    public static void testFindOneEmployeeById() {
        Service service = new Service();
        Employee e = service.findEmployeeById(2L);
        System.out.println(e.getId() + ": " + e.getFirstname() + " " + e.getLastname());
    }
}
