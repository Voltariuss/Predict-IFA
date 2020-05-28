/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author aleryc
 */
@Entity
@DiscriminatorValue("C")
public class Client extends Personne {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String zodiac;
    
    @Column(nullable = false)
    private String chineseAstro;
    
    @Column(nullable = false)
    private String luckyCharm;
    
    @Column(nullable = false)
    private String totem;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZodiac() {
        return zodiac;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public String getChineseAstro() {
        return chineseAstro;
    }

    public void setChineseAstro(String chineseAstro) {
        this.chineseAstro = chineseAstro;
    }

    public String getLuckyCharm() {
        return luckyCharm;
    }

    public void setLuckyCharm(String luckyCharm) {
        this.luckyCharm = luckyCharm;
    }

    public String getTotem() {
        return totem;
    }

    public void setTotem(String totem) {
        this.totem = totem;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.insalyon.b05.predictifa.models.Client[ id=" + id + " ]";
    }
    
}
