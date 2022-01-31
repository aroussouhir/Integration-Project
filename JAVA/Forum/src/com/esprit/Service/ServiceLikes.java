/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.Service;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Likes;
import com.esprit.Entite.Sujet;
import com.esprit.IService.ILikes;
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
public class ServiceLikes implements ILikes<Likes>{
    
    private Connection con;
    private Statement ste;

    public ServiceLikes() {
        con = DataBase.getInstance().getConnection();
    }

    
    public void ajouter(Likes p) throws SQLException {

        PreparedStatement pre=con.prepareStatement("INSERT INTO `forum`.`Likes` ( `sujet_id`,`nomUser` ) VALUES ( ?, ?);");
        
        pre.setInt(1, p.getSujet_id());
        pre.setString(2, p.getNomUser());
           
        pre.executeUpdate();
        
        PreparedStatement statement = con.prepareStatement("UPDATE Sujet SET nbJ = nbJ+1 WHERE `sujet`.`Id` = ?");
        statement.setInt(1, p.getSujet_id());

        statement.executeUpdate();
    }

    
    public boolean delete(Likes p) throws SQLException {

        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from likes");       
        
        while (rs.next()) {                
            if(rs.getInt(1)== p.getId())
            {
                PreparedStatement pre=con.prepareStatement("DELETE FROM `forum`.`likes` WHERE `likes`.`Id` = ?");
                pre.setInt(1, p.getId());
                pre.executeUpdate();

                PreparedStatement statement = con.prepareStatement("UPDATE Sujet SET nbJ = nbJ-1 WHERE `sujet`.`Id` = ?");
                statement.setInt(1, p.getSujet_id());

                statement.executeUpdate();
        
                return true; 
            }
        }
        return false;
    }
   
    public ObservableList<Sujet> userLikesSujet(String nomUser) throws SQLException {
        
        List<Likes> arr1= new ArrayList<>();
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Likes WHERE `likes`.`nomUser` = ?");
        recherche.setString(1, nomUser);
        ResultSet rs = recherche.executeQuery();
        while (rs.next()) { 
            
            int sujet_id=rs.getInt("sujet_id");

                   int id=rs.getInt(1);
                   Likes l =new Likes(id, sujet_id,  nomUser);
            arr1.add(l);
            
         }
        ObservableList<Sujet> arr=FXCollections.observableArrayList();
        
        for (Likes l : arr1 )
        {
            recherche = con.prepareStatement("SELECT * FROM Sujet WHERE `sujet`.`id` = ?");
            recherche.setInt(1, l.getSujet_id());
            rs = recherche.executeQuery();

            while (rs.next()) {                
                       int id=rs.getInt(1);
                       String topic=rs.getString("topic");
                       String question=rs.getString(3); 
                       Date createdAt=rs.getDate("createdAt");
                       int nbJ=rs.getInt("nbJ");
                       int valid=rs.getInt("valid");
                       //int idC=rs.getInt("idC");
                       ServiceCategorie categorie = new ServiceCategorie();
                       Categorie idC = categorie.rechercher(rs.getInt("idC"));
                       
                       Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, idC);
             arr.add(s);
             }
        }
        return arr; 
    }
    
    @Override
    public Likes rechercher(String nomUser, int idS)throws SQLException {
        List<Likes> arr1=new ArrayList<>();
        
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Likes WHERE `likes`.`nomUser` = ?");
        recherche.setString(1, nomUser);
        ResultSet rs = recherche.executeQuery();
        while (rs.next()) { 
            
            int sujet_id=rs.getInt("sujet_id");
            
            int id=rs.getInt(1);
            Likes l =new Likes(id, sujet_id, nomUser);
            arr1.add(l);
         }
        
        for (Likes l : arr1 )
        {
            if(l.getSujet_id() == idS)
            {
                return l ;
            }
        }
        return null;
    }
    
}
