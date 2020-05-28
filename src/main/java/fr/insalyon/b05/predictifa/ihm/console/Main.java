/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.ihm.console;

import fr.insalyon.b05.predictifa.dao.JpaUtil;

/**
 *
 * @author aleryc
 */
public class Main {
    public static void main(String[] args) {
        JpaUtil.init();
        
        System.out.println("succès");
        
        JpaUtil.destroy();
    }
}
