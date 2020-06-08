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

public class Main {
    public static void main(String[] args) throws Exception {
        JpaUtil.init();
        
        // Registration
        testRegistrationCustomer();
        testRegistrationEmployee();
        testRegistrationMedium();
        
        // Search customers, employees and mediums
        testFindCustomer(1L);
        testFindEmployee(2L);
        testFindMedium(1L);
        testFindAllMediums();
        
        // Consultation manipulation
        testInitConsultation(1L, 2L);
        testStartConsultation(1L);
        testFindCustomerCurrentConsultation(1L);
        testFindEmployeeCurrentConsultation(2L);
        testEndConsultation(1L, "Ceci est un commentaire");
        
        // Search of consultations
        testFindCustomerCurrentConsultation(1L);
        testFindCustomerConsultations(1L);
        testFindEmployeeCurrentConsultation(2L);
        testFindEmployeeConsultations(2L);
        
        JpaUtil.destroy();
    }
    
    // ----------------------------------
    // User services : Tests
    // ----------------------------------
    public static void testAuthentificate() {
        // TODO
    }
    
    // ----------------------------------
    // Customer services : Tests
    // ----------------------------------
    public static void testRegistrationCustomer() {
        System.out.println("\n==== Test registration customer =====");
        Service service = new Service();
        Customer customer = new Customer(
            "alerycserrania@gmail.com", 
            "toto", 
            "Aleryc", 
            "Serrania", 
            "11 rue des élites 69100 Villeurbanne", 
            new GregorianCalendar(1997, Calendar.FEBRUARY, 11).getTime(), 
            "0712486521", 
            Gender.M
        );
        try {
            service.registerCustomer(customer);
            System.out.println("Succès de l'enregistrement du client : " + customer);
        } catch (Exception ex) {
            System.out.println("Échec de l'enregistrement du client : " + customer);
        }
    }
    
    public static void testFindCustomer(long id) {
        System.out.println("\n==== Test find customer =====");
        Service service = new Service();
        Customer c = service.findCustomerById(id);
        System.out.println(c.getId() + ": " + c.getFirstname() + " " + c.getLastname());
    }
    
    // ----------------------------------
    // Employee services : Tests
    // ----------------------------------
    public static void testRegistrationEmployee() {
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
        try {
            service.registerEmployee(employee);
            System.out.println("Succès de l'enregistrement de l'employé : " + employee);
        } catch (Exception ex) {
            System.out.println("Échec de l'enregistrement de l'employé : " + employee);
        }
    }
    
    public static void testFindEmployee(long id) {
        System.out.println("\n==== Test find employee =====");
        Service service = new Service();
        Employee e = service.findEmployeeById(id);
        System.out.println(e.getId() + ": " + e.getFirstname() + " " + e.getLastname());
    }
    
    // ----------------------------------
    // Medium services : Tests
    // ----------------------------------
    public static void testRegistrationMedium() {
        System.out.println("\n==== Test registration medium =====");
        Service service = new Service();
        System.out.println("Registration of a fortune teller...");
        FortuneTeller fortuneTeller = new FortuneTeller(
                "Serena",
                Gender.F,
                "Je suis Serena"
        );
        try {
            service.registerMedium(fortuneTeller);
            System.out.println("Succès de l'enregistrement du cartomancien : " + fortuneTeller);
        } catch (Exception ex) {
            System.out.println("Échec de l'enregistrement du cartomancien : " + fortuneTeller);
        }
        System.out.println("Registration of a spiritualist...");
        Spiritualist spiritualist = new Spiritualist(
                "Spiritualist",
                Gender.M,
                "La présentation du spirituel",
                "boule de crystal"
        );
        try {
            service.registerMedium(spiritualist);
            System.out.println("Succès de l'enregistrement du spirituel : " + spiritualist);
        } catch (Exception ex) {
            System.out.println("Échec de l'enregistrement du spirituel : " + spiritualist);
        }
        System.out.println("Registration of a astrologer...");
        Astrologer astrologer = new Astrologer(
                "Astrologer",
                Gender.F,
                "La présentation de l'astrologue",
                "La formation",
                "La promotion"
        );
        try {
            service.registerMedium(astrologer);
            System.out.println("Succès de l'enregistrement de l'astrologue : " + astrologer);
        } catch (Exception ex) {
            System.out.println("Échec de l'enregistrement de l'astrologue : " + astrologer);
        }
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
    
    // ----------------------------------
    // Consultation services : Tests
    // ----------------------------------
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
    
    public static void testStartConsultation(long id) throws Exception {
        System.out.println("\n==== Test start consultation =====");
        Service service = new Service();
        service.startConsultation(id);
    }
    
    public static void testEndConsultation(long id, String commentary) throws Exception {
        System.out.println("\n==== Test end consultation =====");
        Service service = new Service();
        service.endConsultation(id, commentary);
    }
    
    public static void testFindCustomerCurrentConsultation(long id) throws Exception {
        System.out.println("\n==== Test find customer current consultation =====");
        Service service = new Service();
        Consultation consultation = service.getCustomerCurrentConsultation(id);
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
    
    public static void testFindCustomerConsultations(long id) throws Exception {
        System.out.println("\n==== Test find customer consultations =====");
        Service service = new Service();
        List<Consultation> consultations = service.getCustomerConsultations(id);
        if (!consultations.isEmpty()) {
            System.out.println("Consultations trouvées : " + consultations);
        } else {
            System.out.println("Aucune consultation trouvée");
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
    
    // ----------------------------------
    // Statistics services : Tests
    // ----------------------------------
    public static void testGetNbConsultationsPerMedium() {
        // TODO
    }
    
    public static void testGetCustomerDistributionPerEmployee() {
        // TODO
    }
    
    public static void testGetTopMediums() {
        // TODO
    }
}
