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
public class Likes_com {
    
    private int id;
    private int commentaire_id;
    private String nomUser; 

    public Likes_com() {
    }

    public Likes_com(int id, int commentaire_id, String nomUser) {
        this.id = id;
        this.commentaire_id = commentaire_id;
        this.nomUser = nomUser;
    }

    public Likes_com(int commentaire_id, String nomUser) {
        this.commentaire_id = commentaire_id;
        this.nomUser = nomUser;
    }

    @Override
    public String toString() {
        return "Likes_com{" + "commentaire_id=" + commentaire_id + ", nomUser=" + nomUser + '}';
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentaire_id() {
        return commentaire_id;
    }

    public void setCommentaire_id(int commentaire_id) {
        this.commentaire_id = commentaire_id;
    }

    public String getNomUser() {
        return nomUser;
    }

    public void setNomUser(String nomUser) {
        this.nomUser = nomUser;
    }
    
    
    
}
