/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.Client;

/**
 *
 * @author aleryc
 */
public class ClientDAO {
    public void create(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    public Client getById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
}
