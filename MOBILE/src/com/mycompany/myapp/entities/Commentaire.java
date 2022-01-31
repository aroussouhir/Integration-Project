/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;
import java.util.Date;

/**
 *
 * @author arous souhir
 */
public class Commentaire {
    
    private int id;
    private String contenu;
    private Date createdAt;
    private int nbJ;
    private Sujet sujet_id;
    private String nomUser;
    private String emailUser;

    public Commentaire() {
    }

    public Commentaire(int id, String contenu, Date createdAt, int nbJ, Sujet sujet_id, String nomUser) {
        this.id = id;
        this.contenu = contenu;
        this.createdAt = createdAt;
        this.nbJ = nbJ;
        this.sujet_id = sujet_id;
        this.nomUser = nomUser;
    }
    
    public Commentaire(int id, String contenu, Date createdAt, int nbJ, Sujet sujet_id, String nomUser, String emailUser) {
        this.id = id;
        this.contenu = contenu;
        this.createdAt = createdAt;
        this.nbJ = nbJ;
        this.sujet_id = sujet_id;
        this.nomUser = nomUser;
        this.emailUser = emailUser;
    }

    public Commentaire(int id, String contenu, Sujet sujet_id) {
        this.id = id;
        this.contenu = contenu;
        Date today = new Date();
        this.createdAt = today;
        this.nbJ=0;
        this.sujet_id = sujet_id;
        this.nomUser = null ;
    }

    public Commentaire(String contenu, Sujet sujet_id) {
        this.contenu = contenu;
        this.sujet_id = sujet_id;
        Date today = new Date();
        this.createdAt = today;
        this.nbJ=0;
        this.nomUser = null ;
    }
    
    public Commentaire(String contenu, Sujet sujet_id, String nomUser) {
        this.contenu = contenu;
        this.sujet_id = sujet_id;
        Date today = new Date();
        this.createdAt = today;
        this.nbJ=0;
        this.nomUser = nomUser ;
    }
    
    public Commentaire(String contenu, String nomUser, Sujet sujet_id) {
        this.contenu = contenu;
        this.sujet_id = sujet_id;
        Date today = new Date();
        this.createdAt = today;
        this.nbJ=0;
        this.nomUser = nomUser ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
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

    public Sujet getSujet_id() {
        return sujet_id;
    }

    public void setSujet_id(Sujet sujet_id) {
        this.sujet_id = sujet_id;
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
    

    @Override
    public String toString() {
        //return "Commentaire : " + contenu +"\n Auteur : " + nomUser + "\n";
        return nomUser+ " : "+contenu;
    }
    
    
   

    
}
