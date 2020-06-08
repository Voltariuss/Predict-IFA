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
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public class Main {
    public static void main(String[] args) {
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
        
        // Tests statistics
        testGetNbConsultationsPerMedium();
        testGetTopMediums(2);
        
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
        Customer customer = service.findCustomerById(id);
        if (customer != null) {
            System.out.println("Client trouvé : " + customer);
        } else {
            System.out.println("Client introuvable");
        }
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
        Employee employee = service.findEmployeeById(id);
        if (employee != null) {
            System.out.println("Employé trouvé : " + employee);
        } else {
            System.out.println("Employé introuvable");
        }
    }
    
    // ----------------------------------
    // Medium services : Tests
    // ----------------------------------
    public static void testRegistrationMedium() {
        System.out.println("\n==== Test registration medium =====");
        Service service = new Service();
        System.out.println("Enregistrement du cartomancien...");
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
        System.out.println("Enregistrement du spirituel...");
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
        System.out.println("Enregistrement de l'astrologue...");
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
    public static void testInitConsultation(long idCustomer, long idMedium) {
        System.out.println("\n==== Test init consultation =====");
        Service service = new Service();
        try {
            Consultation consultation = service.initConsultation(idCustomer, idMedium);
            System.out.println("Consultation initiée : " + consultation);
        } catch (Exception ex) {
            System.out.println("Impossible d'initier la consultation");
        }
    }
    
    public static void testStartConsultation(long id) {
        System.out.println("\n==== Test start consultation =====");
        Service service = new Service();
        try {
            service.startConsultation(id);
            System.out.println("Début de la consultation d'ID : " + id);
        } catch (Exception ex) {
            System.out.println("Impossible de débuter la consultation d'ID : " + id);
        }
    }
    
    public static void testEndConsultation(long id, String commentary) {
        System.out.println("\n==== Test end consultation =====");
        Service service = new Service();
        try {
            service.endConsultation(id, commentary);
            System.out.println("Fin de la consultation d'ID : " + id);
        } catch (Exception ex) {
            System.out.println("Impossible de mettre fin à la consultation d'ID : " + id);
        }
    }
    
    public static void testFindCustomerCurrentConsultation(long idCustomer) {
        System.out.println("\n==== Test find customer current consultation =====");
        Service service = new Service();
        try {
            Consultation consultation = service.getCustomerCurrentConsultation(idCustomer);
            if (consultation != null) {
                System.out.println("Consultation courante du client d'ID " + idCustomer + " : " + consultation);
            } else {
                System.out.println("Aucune consultation courante pour le client d'ID " + idCustomer);
            }
        } catch (Exception ex) {
            System.out.println("Le client est introuvable");
        }
    }
    
    public static void testFindEmployeeCurrentConsultation(long idEmployee) {
        System.out.println("\n==== Test find employee current consultation =====");
        Service service = new Service();
        try {
            Consultation consultation = service.getEmployeeCurrentConsultation(idEmployee);
            if (consultation != null) {
                System.out.println("Consultation courante de l'employé d'ID " + idEmployee + " : " + consultation);
            } else {
                System.out.println("Aucune consultation courante pour l'employé d'ID " + idEmployee);
            }
        } catch (Exception ex) {
            System.out.println("L'employé est introuvable");
        }
    }
    
    public static void testFindCustomerConsultations(long idCustomer) {
        System.out.println("\n==== Test find customer consultations =====");
        Service service = new Service();
        try {
            List<Consultation> consultations = service.getCustomerConsultations(idCustomer);
            if (!consultations.isEmpty()) {
                System.out.println("Consultations trouvées pour le client d'ID " + idCustomer + " : " + consultations);
            } else {
                System.out.println("Aucune consultation trouvée pour le client d'ID " + idCustomer);
            }
        } catch (Exception ex) {
            System.out.println("Le client est introuvable");
        }
    }
    
    public static void testFindEmployeeConsultations(long idEmployee) {
        System.out.println("\n==== Test find employee consultations =====");
        Service service = new Service();
        try {
            List<Consultation> consultations = service.getEmployeeConsultations(idEmployee);
            if (!consultations.isEmpty()) {
                System.out.println("Consultations trouvées pour l'employé d'ID " + idEmployee + " : " + consultations);
            } else {
                System.out.println("Aucune consultation trouvée");
            }
        } catch (Exception ex) {
            System.out.println("L'employé est introuvable");
        }
    }
    
    // ----------------------------------
    // Statistics services : Tests
    // ----------------------------------
    public static void testGetNbConsultationsPerMedium() {
        System.out.println("\n==== Test get nb consultations per medium =====");
        Service service = new Service();
        Map<Medium, Long> nbConsultationsPerMedium = service.getNbConsultationsPerMedium();
        System.out.println("Nombre de consultations par médium : " + nbConsultationsPerMedium);
    }
    
    public static void testGetCustomerDistributionPerEmployee() {
        // TODO
    }
    
    public static void testGetTopMediums(int nbMediums) {
        System.out.println("\n==== Test get top mediums =====");
        Service service = new Service();
        List<Pair<Medium, Long>> topMediums = service.getTopMediums(nbMediums);
        System.out.println("Top des médiums : " + topMediums);
    }
}
