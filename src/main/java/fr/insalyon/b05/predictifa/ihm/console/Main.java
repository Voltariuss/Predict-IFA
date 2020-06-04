/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.ihm.console;

import fr.insalyon.b05.predictifa.dao.JpaUtil;
import fr.insalyon.b05.predictifa.models.Customer;
import fr.insalyon.b05.predictifa.models.Person;
import fr.insalyon.b05.predictifa.services.Service;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author aleryc
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JpaUtil.init();
        
        //testRegistrationClient();
        testFindOneClientById();
        
        JpaUtil.destroy();
    }
    
    public static void testRegistrationClient() throws Exception {
        Service clientService = new Service();
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
        
        clientService.registration(newClient);
    }
    
    public static void testFindOneClientById() {
        Service clientService = new Service();
        Customer c = clientService.findOneById(1L);
        System.out.println(c.getId() + ": " + c.getFirstname() + " " + c.getLastname());
    }
}
