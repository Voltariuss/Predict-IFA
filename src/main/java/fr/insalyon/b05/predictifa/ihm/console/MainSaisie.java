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
import fr.insalyon.b05.predictifa.models.Gender;
import fr.insalyon.b05.predictifa.models.Person;
import fr.insalyon.b05.predictifa.models.medium.Astrologer;
import fr.insalyon.b05.predictifa.models.medium.FortuneTeller;
import fr.insalyon.b05.predictifa.models.medium.Medium;
import fr.insalyon.b05.predictifa.models.medium.Spiritualist;
import fr.insalyon.b05.predictifa.services.Service;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author aleryc
 */
public class MainSaisie {
    private static Person authenticate(Service service) {
        String login = Saisie.lireChaine("Votre login: ");
        String password = Saisie.lireChaine("Votre mot de passe: ");
        Person person = service.authenticate(login, password);
        if (person == null) {
            System.out.println("Identifiants inconnus");
        } else {
            System.out.println("Vous êtes connectés ! Bienvenue " + person.getFirstname() + " !");
        }
        return person;
    }
    
    private static void registerCustomer(Service service) {
        Integer title = Saisie.lireInteger("Civilité :\n1 - M.\n2 - Mme.", Arrays.asList(1, 2));
        String lastname = Saisie.lireChaine("Nom: ");
        String firstname = Saisie.lireChaine("Prenom: ");
        String postal = Saisie.lireChaine("Adresse Postale : ");
        String telephone = Saisie.lireChaine("Téléphone : ");
        System.out.println("Date de naissance : ");
        Integer day = Saisie.lireInteger("Jour : ");
        Integer month = Saisie.lireInteger("Mois : ");
        Integer year = Saisie.lireInteger("Année : ");
        String mail = Saisie.lireChaine("Email : ");
        String password = Saisie.lireChaine("Mot de passe : ");
        
        Date birthDate = new GregorianCalendar(year, month, day).getTime();
        Gender gender;
        if (title == 1) {
            gender = Gender.M;
        } else {
            gender = Gender.F;
        }
        
        try {
            service.registerCustomer(new Customer(
                mail, password, firstname, lastname, postal, birthDate, firstname, gender));
            System.out.println("Client enregistré");
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
    
    private static void showMediums(Service service) {
        List<Medium> mediums = service.getAllMediums();
        for (Medium medium: mediums) {
            System.out.println(medium);
        }
    }
    
    private static void consult(Service service, Person person) {
        Integer mediumId = Saisie.lireInteger("Id du médium : ");
        try {
            service.initConsultation(person.getId(), mediumId);
            System.out.println("Initialisation de la consultation réussie");
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }
    
    private static void showCustomerCurrentConsultation(Service service, Person person) {
        try {
            Consultation consultation = service.getCustomerCurrentConsultation(person.getId());
            if (consultation == null) {
                System.out.println("Aucune consultation en cours ou en attente");
            } else {
                System.out.println(consultation);
            }
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex);
        }
    }
    
    private static void showEmployeeCurrentConsultation(Service service, Person person) {
        try {
            Consultation consultation = service.getEmployeeCurrentConsultation(person.getId());
            if (consultation == null) {
                System.out.println("Aucune consultation en cours ou en attente");
            } else {
                System.out.println(consultation);
            }
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex);
        }
    }
    
    private static void showCustomerConsultations(Service service, Person person) {
        try {
            List<Consultation> consultations = service.getCustomerConsultations(person.getId());
            for (Consultation consultation: consultations) {
                System.out.println(consultation);
            }
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex);
        }
    }
    
    
    
    private static void showEmployeeConsultations(Service service, Person person) {
        try {
            List<Consultation> consultations = service.getEmployeeConsultations(person.getId());
            for (Consultation consultation: consultations) {
                System.out.println(consultation);
            }
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex);
        }
    }
    
    private static void showCustomerProfile(Service service) {
        Integer customerId = Saisie.lireInteger("Id du client : ");
        Customer customer = service.findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Aucun client trouvé pour l'id: " + customerId);
        } else {
            System.out.println(customer);
        }
    }
    
    private static void startConsultation(Service service) {
        Integer consultationId = Saisie.lireInteger("Id de la consultation : ");
        try {
            service.startConsultation(consultationId);
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex);
        }
    }
    
    private static void endConsultation(Service service) {
        Integer consultationId = Saisie.lireInteger("Id de la consultation : ");
        String commentary = Saisie.lireChaine("Commentaire : ");
        try {
            service.endConsultation(consultationId, commentary);
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex);
        }
    }
    
    private static void showCustomerConsultationsById(Service service) {
        Integer customerId = Saisie.lireInteger("Id du client : ");
        try {
            List<Consultation> consultations = service.getCustomerConsultations(customerId);
            for (Consultation consultation: consultations) {
                System.out.println(consultation);
            }
        } catch (Exception ex) {
            System.out.println("Erreur : " + ex);
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        Person person = null;
        Service service = new Service();
        boolean quit = false;
        
        JpaUtil.init();
        testRegistrationEmployee();
        testRegistrationMedium();
        
        while (!quit) {
            if (person == null) {     
                System.out.println("Bonjour !");
            } else {
                System.out.println("Bonjour " + person.getFirstname() + " " + person.getLastname() + " !");
            }
        
            if (person == null) {
                System.out.println("Liste des services: ");
                System.out.println("1 - Se connecter");
                System.out.println("2 - S'inscrire");
                System.out.println("3 - Liste des médiums");
                System.out.println("0 - Quitter");
                
                int saisie = Saisie.lireInteger("Votre choix: ", Arrays.asList(0, 1, 2, 3));
                switch (saisie) {
                    case 1: 
                        person = authenticate(service);
                        break;
                    case 2:
                        registerCustomer(service);
                        break;
                    case 3:
                        showMediums(service);
                        break;
                    case 0:
                        quit = true;
                        break;
                }
            } else {
                
                if (person instanceof Customer) {
                    System.out.println("Liste des services: ");
                    System.out.println("1 - Se déconnecter");
                    System.out.println("2 - Liste des médiums");
                    System.out.println("3 - Votre profil");
                    System.out.println("4 - Consulter");
                    System.out.println("5 - Consultation courrante");
                    System.out.println("6 - Historique de mes consultations");
                    System.out.println("0 - Quitter");
                    
                    int saisie = Saisie.lireInteger("Votre choix: ", Arrays.asList(0, 1, 2, 3, 4, 5, 6));
                    switch (saisie) {
                        case 1:
                            person = null;
                            break;
                        case 2:
                            showMediums(service);
                            break;
                        case 3:
                            System.out.println(person);
                            break;
                        case 4:
                            consult(service, person);
                            break;
                        case 5:
                            showCustomerCurrentConsultation(service, person);
                            break;
                        case 6:
                            showCustomerConsultations(service, person);
                            break;
                        case 0:
                            quit = true;
                            break;
                    }
                } else {
                    System.out.println("Liste des services: ");
                    System.out.println("1 - Se déconnecter");
                    System.out.println("2 - Liste des médiums");
                    System.out.println("3 - Votre profil");
                    System.out.println("4 - Profil client");
                    System.out.println("5 - Consultation courrante");
                    System.out.println("6 - Commencer consultation courrante");
                    System.out.println("7 - Terminer consultation courrante");
                    System.out.println("8 - Historique de mes consultations");
                    System.out.println("9 - Historique des consultations d'un client");
                    System.out.println("0 - Quitter");
                    
                    int saisie = Saisie.lireInteger("Votre choix: ", Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
                    switch (saisie) {
                        case 1:
                            person = null;
                            break;
                        case 2:
                            showMediums(service);
                            break;
                        case 3:
                            System.out.println(person);
                            break;
                        case 4:
                            showCustomerProfile(service);
                            break;
                        case 5:
                            showEmployeeCurrentConsultation(service, person);
                            break;
                        case 6:
                            startConsultation(service);
                            break;
                        case 7:
                            endConsultation(service);
                            break;
                        case 8:
                            showEmployeeConsultations(service, person);
                            break;
                        case 9:
                            showCustomerConsultationsById(service);
                            break;
                        case 0:
                            quit = true;
                            break;
                    }
                }
            }
        }
        JpaUtil.destroy();
        
    }
    
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
}
