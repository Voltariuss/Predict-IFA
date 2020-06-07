package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.medium.Medium;
import java.util.List;
import javax.persistence.Query;

public class MediumDAO {
    
    public void insert(Medium medium) {
        JpaUtil.obtenirContextePersistance().persist(medium);
    }
    
    public void delete(Medium medium) {
        JpaUtil.obtenirContextePersistance().remove(medium);
    }
    
    public void update(Medium medium) {
        JpaUtil.obtenirContextePersistance().merge(medium);
    }
    
    public Medium find(long id) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }
    
    public List<Medium> findAll() {
        String queryStr = "SELECT m FROM Medium m ORDER BY m.denomination";
        Query query = JpaUtil.obtenirContextePersistance().createQuery(queryStr);
        return query.getResultList();
    }
}
