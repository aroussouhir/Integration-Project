/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.Entite;

/**
 *
 * @author arous souhir
 */
public class Img {
    
    private int id;
    private String ref;
    private int sujet_id;
    private int com_id;

    public Img() {
    }

    public Img(String ref, int sujet_id, int com_id) {
        this.id = id;
        this.ref = ref;
        this.sujet_id = sujet_id;
        this.com_id = com_id;
    }

    public Img(int id, String ref, int sujet_id, int com_id) {
        this.id = id;
        this.ref = ref;
        this.sujet_id = sujet_id;
        this.com_id = com_id;
    }

    public Img(int id) {
        this.id = id;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getSujet_id() {
        return sujet_id;
    }

    public void setSujet_id(int sujet_id) {
        this.sujet_id = sujet_id;
    }

    public int getCom_id() {
        return com_id;
    }

    public void setCom_id(int com_id) {
        this.com_id = com_id;
    }

    @Override
    public String toString() {
        return "Image " + id +"\nSujet : " + sujet_id + "\nCommentaire :" + com_id + '\n';
    }
    
    
}
