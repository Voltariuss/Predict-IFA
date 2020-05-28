/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.services;

import fr.insalyon.b05.predictifa.dao.ClientDAO;
import fr.insalyon.b05.predictifa.dao.JpaUtil;
import fr.insalyon.b05.predictifa.models.Client;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleryc
 */
public class ClientService {
    public void registration(Client client) {
        ClientDAO clientDao = new ClientDAO();
        Client newClient = null;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            clientDao.create(client);
            
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "Client registration: " + newClient);
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Error: ", ex);
            JpaUtil.annulerTransaction();
            throw ex;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }
}
