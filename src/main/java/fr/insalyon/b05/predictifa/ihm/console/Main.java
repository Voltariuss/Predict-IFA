/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.ihm.console;

import fr.insalyon.b05.predictifa.dao.JpaUtil;
import fr.insalyon.b05.predictifa.models.Consultation;
import fr.insalyon.b05.predictifa.models.Customer;
import fr.insalyon.b05.predictifa.models.Gender;
import fr.insalyon.b05.predictifa.models.medium.Medium;
import fr.insalyon.b05.predictifa.models.Employee;
import fr.insalyon.b05.predictifa.models.medium.Astrologer;
import fr.insalyon.b05.predictifa.models.medium.FortuneTeller;
import fr.insalyon.b05.predictifa.models.medium.Spiritualist;
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
        
        testRegistrationCustomer();
        testRegistrationEmployee();
        testFindAllMediums();
        testRegistrationMedium();
        testFindCustomer(1L);
        testFindMedium(1L);
        testFindAllMediums();
        testFindEmployee(2L);
        testFindEmployeeCurrentConsultation(2L);
        testFindEmployeeConsultations(2L);
        testInitConsultation(1L, 1L);
        testStartConsultation();
        testEndConsultation();
        
        JpaUtil.destroy();
    }
    
    public static void testInitConsultation(long idCustomer, long idMedium) throws Exception {
        System.out.println("\n==== Test init consultation =====");
        Service service = new Service();
        try {
            Consultation consultation = service.initConsultation(idCustomer, idMedium);
            System.out.println("Consultation initiée : " + consultation);
        } catch (Exception ex) {
            System.out.println("Impossible d'initier la consultation");
        }
    }
    
    public static void testStartConsultation() throws Exception {
        System.out.println("\n==== Test start consultation =====");
        Service service = new Service();
        service.startConsultation(51);
    }
    
    public static void testEndConsultation() throws Exception {
        System.out.println("\n==== Test end consultation =====");
        Service service = new Service();
        service.endConsultation(51);
    }
    
    
    public static void testFindCustomerCurrentConsultation() throws Exception {
        System.out.println("\n==== Test find customer current consultation =====");
        Service service = new Service();
        Consultation consultation = service.getCustomerCurrentConsultation(1L);
        System.out.println(consultation); 
    }
    
    public static void testFindEmployeeCurrentConsultation(long id) throws Exception {
        System.out.println("\n==== Test find employee current consultation =====");
        Service service = new Service();
        Consultation consultation = service.getEmployeeCurrentConsultation(id);
        if (consultation != null) {
            System.out.println("Consultation courante trouvée : " + consultation);
        } else {
            System.out.println("Pas de consultation courante");
        }
    }
    
    public static void testFindEmployeeConsultations(long id) throws Exception {
        System.out.println("\n==== Test find employee consultations =====");
        Service service = new Service();
        List<Consultation> consultations = service.getEmployeeConsultations(id);
        if (!consultations.isEmpty()) {
            System.out.println("Consultations trouvées : " + consultations);
        } else {
            System.out.println("Aucune consultation trouvée");
        }
    }
    
    public static void testRegistrationCustomer() throws Exception {
        System.out.println("\n==== Test registration customer =====");
        Service service = new Service();
        Customer newClient = new Customer(
            "alerycserrania@gmail.com", 
            "toto", 
            "Aleryc", 
            "Serrania", 
            "11 rue des élites 69100 Villeurbanne", 
            new GregorianCalendar(1997, Calendar.FEBRUARY, 11).getTime(), 
            "0712486521", 
            Gender.M
        );
        
        service.registerCustomer(newClient);
    }
    
    public static void testRegistrationEmployee() throws Exception {
        System.out.println("\n==== Test registration employee =====");
        Service service = new Service();
        Employee employee = new Employee(
                "0685735498",
                "employee@mail.com",
                "passwordEmployee",
                "First name employee",
                "Last name employee",
                "Postal address employee",
                new GregorianCalendar(1999, Calendar.FEBRUARY, 26).getTime(),
                "0698547896",
                Gender.M
        );
        service.registerEmployee(employee);
    }
    
    public static void testRegistrationMedium() throws Exception {
        System.out.println("\n==== Test registration medium =====");
        Service service = new Service();
        System.out.println("Registration of a fortune teller...");
        FortuneTeller fortuneTeller = new FortuneTeller(
                "Serena",
                Gender.F,
                "Je suis Serena"
        );
        service.registerMedium(fortuneTeller);
        System.out.println("Registration of a spiritualist...");
        Spiritualist spiritualist = new Spiritualist(
                "Spiritualist",
                Gender.M,
                "La présentation du spirituel",
                "boule de crystal"
        );
        service.registerMedium(spiritualist);
        System.out.println("Registration of a astrologer...");
        Astrologer astrologer = new Astrologer(
                "Astrologer",
                Gender.F,
                "La présentation de l'astrologue",
                "La formation",
                "La promotion"
        );
        service.registerMedium(astrologer);
    }
    
    public static void testFindMedium(long id) {
        System.out.println("\n==== Test find medium =====");
        Service service = new Service();
        Medium medium = service.getMediumById(id);
        if (medium != null) {
            System.out.println("Medium trouvé : " + medium);
        } else {
            System.out.println("Medium introuvable");
        }
    }
    
    public static void testFindAllMediums() {
        System.out.println("\n==== Test find all mediums =====");
        Service service = new Service();
        List<Medium> mediums = service.getAllMediums();
        if (!mediums.isEmpty()) {
            System.out.println("Liste des médiums trouvés : " + mediums);
        } else {
            System.out.println("Aucun médium trouvé");
        }
    }
    
    public static void testFindCustomer(long id) {
        System.out.println("\n==== Test find customer =====");
        Service service = new Service();
        Customer c = service.findCustomerById(id);
        System.out.println(c.getId() + ": " + c.getFirstname() + " " + c.getLastname());
    }
    
    public static void testFindEmployee(long id) {
        System.out.println("\n==== Test find employee =====");
        Service service = new Service();
        Employee e = service.findEmployeeById(id);
        System.out.println(e.getId() + ": " + e.getFirstname() + " " + e.getLastname());
    }
}
