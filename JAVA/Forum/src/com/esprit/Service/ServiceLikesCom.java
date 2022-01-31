/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.Service;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Likes_com;
import com.esprit.Entite.Sujet;
import com.esprit.IService.ILikes_com;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import com.esprit.Utils.DataBase;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author arous souhir
 */
public class ServiceLikesCom implements ILikes_com<Likes_com>{
    
    private Connection con;
    private Statement ste;

    public ServiceLikesCom() {
        con = DataBase.getInstance().getConnection();
    }

    
    public void ajouter(Likes_com p) throws SQLException {

        PreparedStatement pre=con.prepareStatement("INSERT INTO `forum`.`Likes_com` ( `commentaire_id`,`nomUser` ) VALUES ( ?, ?);");
        
        pre.setInt(1, p.getCommentaire_id());
        pre.setString(2, p.getNomUser());
           
        pre.executeUpdate();
        
        PreparedStatement statement = con.prepareStatement("UPDATE Commentaire SET nbJ = nbJ+1 WHERE `commentaire`.`Id` = ?");
        statement.setInt(1, p.getCommentaire_id());

        statement.executeUpdate();
    }

    
    public boolean delete(Likes_com p) throws SQLException {

        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from likes_com");       
        
        while (rs.next()) {                
            if(rs.getInt(1)== p.getId())
            {
                PreparedStatement pre=con.prepareStatement("DELETE FROM `forum`.`likes_com` WHERE `likes_com`.`Id` = ?");
                pre.setInt(1, p.getId());
                pre.executeUpdate();

                PreparedStatement statement = con.prepareStatement("UPDATE Commentaire SET nbJ = nbJ-1 WHERE `commentaire`.`Id` = ?");
                statement.setInt(1, p.getCommentaire_id());

                statement.executeUpdate();
        
                return true; 
            }
        }
        return false;
    }
   
    public ObservableList<Commentaire> userLikesCommentaire(String nomUser) throws SQLException {
        
        List<Likes_com> arr1= new ArrayList<>();
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Likes_com WHERE `likes_com`.`nomUser` = ?");
        recherche.setString(1, nomUser);
        ResultSet rs = recherche.executeQuery();
        while (rs.next()) { 
            
            int commentaire_id=rs.getInt("commentaire_id");

                   int id=rs.getInt(1);
                   Likes_com l =new Likes_com(id, commentaire_id,  nomUser);
            arr1.add(l);
            
         }
        ObservableList<Commentaire> arr=FXCollections.observableArrayList();
        
        for (Likes_com l : arr1 )
        {
            recherche = con.prepareStatement("SELECT * FROM Commentaire WHERE `commentaire`.`id` = ?");
            recherche.setInt(1, l.getCommentaire_id());
            rs = recherche.executeQuery();

            while (rs.next()) {                
                       int id=rs.getInt(1);
                       String contenu=rs.getString("contenu");
                       Date createdAt=rs.getDate("createdAt");
                       int nbJ=rs.getInt("nbJ");
                       String emailUser=rs.getString("emailUser");
                       ServiceSujet ss = new ServiceSujet();
                       Sujet commentaire_id = ss.rechercher(rs.getInt("sujet_id"));
                       
                       Commentaire s=new Commentaire(id, contenu, createdAt, nbJ,commentaire_id, nomUser, emailUser);
             arr.add(s);
             }
        }
        return arr; 
    }
    
    @Override
    public Likes_com rechercher(String nomUser, int idC)throws SQLException {
        List<Likes_com> arr1=new ArrayList<>();
        
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Likes_com WHERE `likes_com`.`nomUser` = ?");
        recherche.setString(1, nomUser);
        ResultSet rs = recherche.executeQuery();
        while (rs.next()) { 
            
            int commentaire_id=rs.getInt("commentaire_id");
            
            int id=rs.getInt(1);
            Likes_com l =new Likes_com(id, commentaire_id, nomUser);
            arr1.add(l);
         }
        
        for (Likes_com l : arr1 )
        {
            if(l.getCommentaire_id() == idC)
            {
                return l ;
            }
        }
        return null;
    }
    
}

