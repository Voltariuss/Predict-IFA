package fr.insalyon.b05.predictifa.dao;

import fr.insalyon.b05.predictifa.models.medium.Medium;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
    
    public Map<Medium, Long> getNbConsultationsPerMedium() {
        String queryStr = "SELECT m, COUNT(c)\n"
                + "FROM Medium m\n"
                + "LEFT JOIN m.consultations c\n"
                + "GROUP BY m";
        Query query = JpaUtil.obtenirContextePersistance().createQuery(queryStr);
        List<Object[]> resultList = query.getResultList();
        Map<Medium, Long> nbConsultationsPerMedium = new HashMap<>();
        for (Object[] objects : resultList) {
            Medium medium = (Medium) objects[0];
            long nbConsultations = (long) objects[1];
            nbConsultationsPerMedium.put(medium, nbConsultations);
        }
        return nbConsultationsPerMedium;
    }
    
    public List<Pair<Medium, Long>> getTopMediums(int nbMediums) {
        String queryStr = "SELECT m, COUNT(c)\n"
                + "FROM Medium m\n"
                + "LEFT JOIN m.consultations c\n"
                + "GROUP BY m\n"
                + "ORDER BY COUNT(c) DESC";
        Query query = JpaUtil.obtenirContextePersistance().createQuery(queryStr);
        List<Object[]> resultList = query.setMaxResults(nbMediums).getResultList();
        List<Pair<Medium, Long>> topMediums = new ArrayList<>(nbMediums);
        for (Object[] objects : resultList) {
            Medium medium = (Medium) objects[0];
            long nbConsultations = (long) objects[1];
            Pair<Medium, Long> pair = new ImmutablePair<>(medium, nbConsultations);
            topMediums.add(pair);
        }
        return topMediums;
    }
}
