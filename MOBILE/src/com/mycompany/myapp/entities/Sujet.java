/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;
import java.util.Objects;
import javafx.scene.control.Button;

/**
 *
 * @author arous souhir
 */
public class Sujet {
    private int id;
    private String topic;
    private String question;
    private Date createdAt;
    private int nbJ;
    private int valid;
    private String nomUser;
    private String emailUser;
    private Categorie idC;
    

    
    
    public Sujet() {
    }

    public Sujet(String topic, String question) {
        //this.id = id;
        this.topic = topic;
        this.question = question;
        this.idC = null;
        Date today = new Date();
        this.createdAt = today;
        this.nbJ = 0;
        this.valid = 0;
    }
        
    public Sujet(int id, String topic, String question, Categorie idC) {
        this.id = id;
        this.topic = topic;
        this.question = question;
        this.idC = idC;
        Date today = new Date();
        this.createdAt = today;
        this.nbJ = 0;
        this.valid = 0;
    }

    public Sujet(int id, String topic, String question, Date createdAt, int nbJ, int valid, String nomUser, Categorie idC) {
        this.id = id;
        this.topic = topic;
        this.question = question;
        this.createdAt = createdAt;
        this.nbJ = nbJ;
        this.valid = valid;
        this.nomUser = nomUser;
        this.idC = idC;
    }
    
    public Sujet(int id, String topic, String question, Date createdAt, int nbJ, int valid, String nomUser, String emailUser, Categorie idC) {
        this.id = id;
        this.topic = topic;
        this.question = question;
        this.createdAt = createdAt;
        this.nbJ = nbJ;
        this.valid = valid;
        this.nomUser = nomUser;
        this.emailUser = emailUser;
        this.idC = idC;
    }

    public Sujet(String topic, String question, Categorie idC) {
        this.topic = topic;
        this.question = question;
        Date today = new Date();
        this.createdAt = today;
        this.nbJ = 0;
        this.valid = 0;
        this.nomUser = null ;
        this.idC = idC;
    }

    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long date) {
        this.createdAt = new Date(date * 1000);
    }

    public int getNbJ() {
        return nbJ;
    }

    public void setNbJ(int nbJ) {
        this.nbJ = nbJ;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public Categorie getIdC() {
        return idC;
    }

    public void setIdC(Categorie idC) {
        this.idC = idC;
    }

    @Override
    public String toString() {
        return "Sujet : " + topic + "\nAjoute le : " + createdAt + "\n" + nbJ + " J'aime \n";
    }
    
    public String toString2() {
        return "Categorie : "+ idC.getNom()+ "\nSujet : " + topic + "\nQuestion : " + question ;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sujet other = (Sujet) obj;
        if (!Objects.equals(this.topic, other.topic)) {
            return false;
        }
        return true;
    }
    
}
