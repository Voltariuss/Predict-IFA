/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.Employee;

/**
 *
 * @author aleryc
 */
public class EmployeeDAO {
    public Employee getById(long id) {
        return JpaUtil.obtenirContextePersistance().find(Employee.class, id);
    }
}
