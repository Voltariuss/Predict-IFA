/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.b05.predictifa.models;

import java.io.Serializable;
import java.util.Date;
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
public class Client extends Person {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String zodiac;
    
    @Column(nullable = false)
    private String chineseAstro;
    
    @Column(nullable = false)
    private String luckyColor;
    
    @Column(nullable = false)
    private String totem;

    protected Client() {}
    public Client(String zodiac, String chineseAstro, String luckyCharm, String totem, String mail, String password, String firstname, String lastname, String postalAddress, Date birthDate, String phoneNumber, Gender genre) {
        super(mail, password, firstname, lastname, postalAddress, birthDate, phoneNumber, genre);
        this.zodiac = zodiac;
        this.chineseAstro = chineseAstro;
        this.luckyColor = luckyCharm;
        this.totem = totem;
    }
    
    

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
        return luckyColor;
    }

    public void setLuckyCharm(String luckyCharm) {
        this.luckyColor = luckyCharm;
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
