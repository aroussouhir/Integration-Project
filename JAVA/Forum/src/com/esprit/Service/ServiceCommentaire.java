/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.Service;

import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Sujet;
import com.esprit.IService.ICommentaire;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import com.esprit.Utils.DataBase;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author arous souhir
 */
public class ServiceCommentaire implements ICommentaire<Commentaire> {
    
    private Connection con;
    private Statement ste;

    public ServiceCommentaire() {
        con = DataBase.getInstance().getConnection();
    }

    
    public void ajouter(Commentaire p) throws SQLException {

    PreparedStatement pre=con.prepareStatement("INSERT INTO `forum`.`Commentaire` ( `contenu`, `createdAt`, `nbJ`, `sujet_id`, `nomUser`, `emailUser` ) VALUES ( ?, ?, ?, ?, ?, ? );");

    pre.setString(1, p.getContenu());
    pre.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
    pre.setInt(3, p.getNbJ());
    pre.setInt(4, p.getSujet_id().getId());
    pre.setString(5, p.getNomUser());
    pre.setString(6, p.getEmailUser());

    pre.executeUpdate();
    }
   
    public boolean delete(Commentaire p) throws SQLException {

        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from Commentaire");       
        
        while (rs.next()) {                
            if(rs.getInt(1)== p.getId())
            {
                PreparedStatement pre=con.prepareStatement("DELETE FROM `forum`.`Commentaire` WHERE `Commentaire`.`Id` = ?");
                pre.setInt(1, p.getId());
                
                ResultSet rsI=ste.executeQuery("select * from Img");  
                
                while (rsI.next()) {                
                    if(rsI.getInt("com_id")== p.getId())
                    {
                        PreparedStatement preI=con.prepareStatement("DELETE FROM `forum`.`img` WHERE `img`.`com_id` = ?");
                        preI.setInt(1, p.getId());
                        preI.executeUpdate();
                        pre.executeUpdate();
                        return true; 
                    }
                }
                pre.executeUpdate();
                return true; 
            }
        }
        return false;
    }
   
    public boolean update(Commentaire t) throws SQLException {

        String sql = "UPDATE Commentaire SET contenu=? WHERE `commentaire`.`Id` = ?";
 
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, t.getContenu());
        statement.setInt(2, t.getId());

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0)
        {
            return true;
        }
        return false;
    }

    
    public void show(Commentaire t) throws SQLException {

        System.out.println(t.toString()); 
    }

    
    public Commentaire rechercher(int id) throws SQLException {

        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Commentaire WHERE `commentaire`.`Id` = ?");
        recherche.setInt(1, id);

        ResultSet rs = recherche.executeQuery();
        
            while (rs.next()) {   
                
               String contenu=rs.getString("contenu");
               Date createdAt=rs.getDate("createdAt");
               int nbJ=rs.getInt("nbJ");
               String nomUser=rs.getString("nomUser");
               String emailUser=rs.getString("emailUser");
               //int sujet_id=rs.getInt("sujet_id");
               ServiceSujet sujet = new ServiceSujet();
               Sujet sujet_id = sujet.rechercher(rs.getInt("sujet_id"));
               
               Commentaire c =new Commentaire(id, contenu, createdAt, nbJ, sujet_id, nomUser, emailUser);
               return c;
     }
    return null;
    }

    @Override
    public List<Commentaire> readCommentaires(int sujet_id) throws SQLException {

        List<Commentaire> arr=new ArrayList<>();
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select * from Commentaire WHERE commentaire.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String contenu=rs.getString("contenu");
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceSujet sujet = new ServiceSujet();
                   Sujet s = sujet.rechercher(sujet_id);

                   Commentaire c =new Commentaire(id, contenu, createdAt, nbJ, s, nomUser, emailUser);
                   arr.add(c);
         }
        return arr;
    }

    
    public ObservableList<Commentaire> triNbJ(int sujet_id) throws SQLException {

        ObservableList<Commentaire> arr= FXCollections.observableArrayList();
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from Commentaire WHERE commentaire.sujet_id = '"+ sujet_id +"'ORDER BY nbJ desc");

        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String contenu=rs.getString("contenu");
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceSujet sujet = new ServiceSujet();
                   Sujet s = sujet.rechercher(sujet_id);

                   Commentaire c =new Commentaire(id, contenu, createdAt, nbJ, s, nomUser, emailUser);
                   arr.add(c);
         }
        return arr;
    }
    
    public ObservableList<Commentaire> commentairesUser(String nomUser) throws SQLException {

        ObservableList<Commentaire> arr= FXCollections.observableArrayList();
        PreparedStatement recherche = con.prepareStatement("select * from Commentaire WHERE `commentaire`.`nomUser` = ?");
        recherche.setString(1, nomUser);
        ResultSet rs = recherche.executeQuery();
    
        while (rs.next()) {                
                   int id=rs.getInt("id");
                   String contenu=rs.getString("contenu");
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   String emailUser=rs.getString("emailUser");
                   ServiceSujet sujet = new ServiceSujet();
                   Sujet sujet_id = sujet.rechercher(rs.getInt("sujet_id"));

                   Commentaire c =new Commentaire(id, contenu, createdAt, nbJ, sujet_id, nomUser, emailUser);
                   arr.add(c);
         }
        return arr;  
    }

    @Override
    public ObservableList<Commentaire> readCommentaires2(int sujet_id) throws SQLException {
        ObservableList<Commentaire> arr=FXCollections.observableArrayList();
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select * from Commentaire WHERE commentaire.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String contenu=rs.getString("contenu");
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceSujet sujet = new ServiceSujet();
                   Sujet s = sujet.rechercher(sujet_id);

                   Commentaire c =new Commentaire(id, contenu, createdAt, nbJ, s, nomUser, emailUser);
                   arr.add(c);
         }
        return arr;
    }

    @Override
    public boolean exist(Commentaire t) throws SQLException {
        
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Commentaire WHERE `commentaire`.`contenu` = ?");
        recherche.setString(1, t.getContenu());

        ResultSet rs = recherche.executeQuery();

        int x=0;
        while(rs.next())
        {
            x++;
        }
        if(x==0) { return false;}
    return true;
    }
    
    
}
