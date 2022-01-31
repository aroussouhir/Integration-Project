/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author arous souhir
 */
public class Likes {
    
    private int id;
    private int sujet_id;
    private String nomUser;

    public Likes() {
    }

    public Likes(int id, int sujet_id, String nomUser) {
        this.id = id;
        this.sujet_id = sujet_id;
        this.nomUser = nomUser;
    }

    public Likes(int sujet_id, String nomUser) {
        this.sujet_id = sujet_id;
        this.nomUser = nomUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSujet_id() {
        return sujet_id;
    }

    public void setSujet_id(int sujet_id) {
        this.sujet_id = sujet_id;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }
    
}
