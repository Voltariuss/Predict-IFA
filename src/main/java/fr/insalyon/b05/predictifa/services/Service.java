package fr.insalyon.b05.predictifa.services;

import fr.insalyon.b05.predictifa.util.AstroNet;
import fr.insalyon.b05.predictifa.dao.ConsultationDAO;
import fr.insalyon.b05.predictifa.dao.CustomerDAO;
import fr.insalyon.b05.predictifa.dao.EmployeeDAO;
import fr.insalyon.b05.predictifa.dao.JpaUtil;
import fr.insalyon.b05.predictifa.dao.MediumDAO;
import fr.insalyon.b05.predictifa.dao.PersonDAO;
import fr.insalyon.b05.predictifa.models.Consultation;
import fr.insalyon.b05.predictifa.models.Customer;
import fr.insalyon.b05.predictifa.models.Employee;
import fr.insalyon.b05.predictifa.models.Gender;
import fr.insalyon.b05.predictifa.models.medium.Medium;
import fr.insalyon.b05.predictifa.models.Person;
import fr.insalyon.b05.predictifa.util.Message;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {
    
    // ----------------------------------
    // Message services
    // ----------------------------------
    private static final String CONTACT_MAIL = "contact@predict.ifa";
   
    private void sendNotifRegistrationSuccess(Customer customer) {
        StringWriter mail = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(mail);
        
        mailWriter.print("Bonjour ");
        mailWriter.print(customer.getFirstname());
        mailWriter.println(", nous vous confirmons votre inscription au service PREDICT’IFA.");
        mailWriter.println("Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos médiums.");
        
        Message.envoyerMail(
            CONTACT_MAIL, 
            customer.getMail(), 
            "Bienvenue chez PREDICT’IFA", 
            mail.toString()
        ); 
    }
    
    private void sendNotifRegistrationFail(Customer customer) {
        StringWriter mail = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(mail);
        
        mailWriter.print("Bonjour ");
        mailWriter.print(customer.getFirstname());
        mailWriter.println(", votre inscription au service PREDICT’IFA a malencontreusement échoué...");
        mailWriter.println("Merci de recommencer ultérieurement.");
        
        Message.envoyerMail(
            CONTACT_MAIL, 
            customer.getMail(), 
            "Échec de l’inscription chez PREDICT’IFA", 
            mail.toString()
        ); 
    }
    
    private void sendNotifConsultationRequest(Customer customer, Employee employee, Medium medium) {
        StringWriter mailEmployee = new StringWriter();
        PrintWriter mailEmployeeWriter = new PrintWriter(mailEmployee);
        
        mailEmployeeWriter.print("Bonjour ");
        mailEmployeeWriter.print(employee.getFirstname());
        mailEmployeeWriter.println(".");
        mailEmployeeWriter.print("Consultation requise pour ");
        
        if (customer.getGender() == Gender.F) {
            mailEmployeeWriter.print("Mme ");
        } else {
            mailEmployeeWriter.print("M ");
        }
        
        mailEmployeeWriter.print(customer.getFirstname());
        mailEmployeeWriter.print(" ");
        mailEmployeeWriter.println(customer.getLastname());
        mailEmployeeWriter.print("Médium à incarner : ");
        mailEmployeeWriter.println(medium.getDenomination());
        
        Message.envoyerNotification(
            employee.getProPhoneNumber(), 
            mailEmployee.toString()
        );
    }
    
    private void sendNotifConsultationStart(Consultation consultation, Employee employee, Customer customer, Medium medium) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm");
        
        StringWriter mailCustomer = new StringWriter();
        PrintWriter mailCustomerWriter = new PrintWriter(mailCustomer);
        
        mailCustomerWriter.print("Bonjour ");
        mailCustomerWriter.print(customer.getFirstname());
        mailCustomerWriter.println(".");
        mailCustomerWriter.print("J'ai bien reçu votre demande de consultation du ");
        mailCustomerWriter.print(dateFormat.format(consultation.getRequestDate()));
        mailCustomerWriter.println(".");
        mailCustomerWriter.print("Vous pouvez dès à présent me contacter au ");
        mailCustomerWriter.print(employee.getProPhoneNumber());
        mailCustomerWriter.println(". À tout de suite !");
        mailCustomerWriter.print("Médiumiquement vôtre, ");
        mailCustomerWriter.println(medium.getDenomination());
        
        Message.envoyerNotification(
            customer.getPhoneNumber(), 
            mailCustomer.toString()
        );
        
    }
    
    
    // ----------------------------------
    // User services
    // ----------------------------------
    public Person authenticate(String login, String password) {
        PersonDAO personDao = new PersonDAO();
        
        JpaUtil.creerContextePersistance();
        Person person = personDao.getByLogin(login, password);
        JpaUtil.fermerContextePersistance();
        
        Logger.getAnonymousLogger().log(Level.INFO, "Success - authenticate: " + personDao);
        
        return person;
    }
    
    // ----------------------------------
    // Customer services
    // ----------------------------------
    public void registerCustomer(Customer customer) throws Exception {
        // TODO : use AstroNet to fetch profile
        
        AstroNet astro = new AstroNet();
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
            
            // Envoyer mail de notification succès au client
            sendNotifRegistrationSuccess(customer);
            
            Logger.getAnonymousLogger().log(Level.INFO, "Success - Customer registration: " + customer);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - Customer registration: " + customer, ex);
            JpaUtil.annulerTransaction();
            
            // Envoyer mail de notification erreur au client
            sendNotifRegistrationFail(customer);
            
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
    // Employee services
    // ----------------------------------
    public void registerEmployee(Employee employee) throws Exception {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            employeeDAO.insert(employee);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.FINE, "Success - Employee registration: {0}", employee);
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - Employee registration: " + employee, ex);
            throw ex;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
    
    public Employee findEmployeeById(long id) {
        EmployeeDAO employeeDao = new EmployeeDAO();
       
        JpaUtil.creerContextePersistance();
        Employee employee = employeeDao.find(id);
        JpaUtil.fermerContextePersistance();
        
        Logger.getAnonymousLogger().log(Level.INFO, "Success - Get employee by id: " + employee);
        
        return employee;
    }
    
    // ----------------------------------
    // Medium services
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
    // Consultation services
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
        Consultation currentConsultation = consultationDao.getCustomerCurrentConsultation(customer);
        if (currentConsultation != null) {
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
            
            // Envoyer SMS de notification à l'employé 
            sendNotifConsultationRequest(customer, employee, medium);
            
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
            
            // Envoyer SMS de notification au client 
            sendNotifConsultationStart(consultation, consultation.getEmployee(), consultation.getCustomer(), consultation.getMedium());
            
            Logger.getAnonymousLogger().log(Level.INFO, "Success - startConsultation");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - startConsultation: error update", ex);
            JpaUtil.annulerTransaction();
            throw new Exception("Error update");
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
    }
    
    public void endConsultation(long idConsultation, String commentary)throws Exception {
        ConsultationDAO consultationDao = new ConsultationDAO();
        
        JpaUtil.creerContextePersistance();
        
        Consultation consultation = consultationDao.getById(idConsultation);
        if (consultation == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - endConsultation: Consultation not found");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Consultation not found");
        }
        
        if (consultation.getStartDate() == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - endConsultation: Consultation not started");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Consultation not started");
        }
        
        if (consultation.getEndDate() != null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error - endConsultation: Consultation already ended");       
            JpaUtil.fermerContextePersistance();
            throw new Exception("Consultation already ended");
        }
        
        consultation.setEndDate(new Date());
        consultation.setCommentary(commentary);
        
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
        Employee employee = employeeDAO.find(idEmployee);
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
        Employee employee = employeeDao.find(idEmployee);
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
    
    // ----------------------------------
    // Statistics services
    // ----------------------------------
    public Map<Medium, Integer> getNbConsultationsPerMedium() {
        return null;
    }
    
    public Map<Employee, Map<Customer, Integer>> getCustomerDistributionPerEmployee() {
        return null;
    }
    
    public List<Map.Entry<Medium, Integer>> getTopMediums(int nbMediums) {
        return null;
    }
}
