/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.ihm.console;

import fr.insalyon.b05.predictifa.dao.JpaUtil;
import fr.insalyon.b05.predictifa.models.Client;
import fr.insalyon.b05.predictifa.models.Person;
import fr.insalyon.b05.predictifa.services.ClientService;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author aleryc
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JpaUtil.init();
        
        testRegistrationClient();
        
        JpaUtil.destroy();
    }
    
    public static void testRegistrationClient() throws Exception {
        ClientService clientService = new ClientService();
        Client newClient = new Client(
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
}
