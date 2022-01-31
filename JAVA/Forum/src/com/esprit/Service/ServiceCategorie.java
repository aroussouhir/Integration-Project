/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.Service;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Sujet;
import com.esprit.IService.ICategorie;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import com.esprit.Utils.DataBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class ServiceCategorie implements ICategorie<Categorie>{

    private Connection con;
    private Statement ste;

    public ServiceCategorie() {
        con = DataBase.getInstance().getConnection();
    }
    
    @Override
    public void ajouter(Categorie p) throws SQLException {

        PreparedStatement pre=con.prepareStatement("INSERT INTO `forum`.`Categorie` ( `nom` ) VALUES ( ? );");

        pre.setString(1, p.getNom());
        pre.executeUpdate();
    }

    @Override
    public boolean delete(Categorie p) throws SQLException {

        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from Categorie");       
        
        while (rs.next()) {                
            if(rs.getInt(1)== p.getId())
            {
                PreparedStatement pre=con.prepareStatement("DELETE FROM `forum`.`Categorie` WHERE `categorie`.`Id` = ?");
                pre.setInt(1, p.getId());
                
                ResultSet rsI=ste.executeQuery("select * from Sujet");  
                
                while (rsI.next()) {                
                    if(rsI.getInt("idC")== p.getId())
                    {
                        PreparedStatement preI=con.prepareStatement("DELETE FROM `forum`.`sujet` WHERE `sujet`.`idC` = ?");
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

    @Override
    public boolean update(Categorie t) throws SQLException {

        //Saisie temporaire
        int x=0;
        Scanner saisie = new Scanner(System.in);
        
        System.out.println("Categorie : "+t.getNom());
        String nom = saisie.next();
        if(!nom.equals("non")) { t.setNom(nom); x=1; }
        
        //Modification
        
        String sql = "UPDATE Categorie SET nom=? WHERE `categorie`.`Id` = ?";
 
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, t.getNom());
        statement.setInt(2, t.getId());
        
        statement.executeUpdate();
        if(x==1)
        {
            return true;
        }
        return false;
    }

    @Override
    public List<Categorie> readAll() throws SQLException {

        List<Categorie> arr=new ArrayList<>();
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from Categorie");
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String nom=rs.getString("nom");

                   Categorie c=new Categorie(id, nom);
         arr.add(c);
         }
        return arr;
    }

    @Override
    public Categorie rechercher(int id) throws SQLException {

        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Categorie WHERE `categorie`.`Id` = ?");
        recherche.setInt(1, id);

        ResultSet rs = recherche.executeQuery();
        
            while (rs.next()) {   
                
               String nom=rs.getString("nom");
               
               Categorie c =new Categorie(id, nom);
               return c;
     }
    return null;
    }
    
    public void show(Categorie t) throws SQLException {
        
        System.out.println(t.toString());   
    }

    @Override
    public List<Sujet> readSujets(int idC) throws SQLException {

        List<Sujet> arr=new ArrayList<>();
        ste=con.createStatement();
    //    ResultSet rs=ste.executeQuery("select * from Sujet inner join categorie on sujet.idC = '"+ idC +"'");
        ResultSet rs=ste.executeQuery("select * from Sujet WHERE sujet.idC = '"+ idC +"'");
    
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question= rs.getString("question");  
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   Categorie cat = rechercher(idC);

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, cat);
         arr.add(s);
         }
        return arr;
    }

    @Override
    public ObservableList<String> readAllNew() throws SQLException {

        ObservableList<String> arr= FXCollections.observableArrayList();
        ste=con.createStatement();
        ResultSet rs = ste.executeQuery("select nom from categorie");
        while(rs.next()){
            String nom=rs.getString(1);
            arr.add(nom);
        }
        return arr;
    }

    @Override
    public int idCateg(String nomCateg) throws SQLException {

    int id = -1;
    ste=con.createStatement();
    ResultSet rs = ste.executeQuery("select id from categorie c where c.nom ='"+nomCateg+"'");
    while (rs.next()){
        id= rs.getInt(1);
    }
    return id;
    }

    @Override
    public ObservableList<Categorie> readAll2() throws SQLException {

        ObservableList<Categorie> arr=FXCollections.observableArrayList();
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from Categorie");
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String nom=rs.getString("nom");

                   Categorie c=new Categorie(id, nom);
         arr.add(c);
         }
        return arr;
    }

    @Override
    public ObservableList<Sujet> readSujets2(int idC) throws SQLException {
        ObservableList<Sujet> arr=FXCollections.observableArrayList();
        ste=con.createStatement();
    //    ResultSet rs=ste.executeQuery("select * from Sujet inner join categorie on sujet.idC = '"+ idC +"'");
        ResultSet rs=ste.executeQuery("select * from Sujet WHERE sujet.valid =1 and  sujet.idC = '"+ idC +"'");
    
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question= rs.getString("question");  
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   String emailUser = rs.getString("emailUser");
                   Categorie cat = rechercher(idC);

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser,emailUser, cat);
         arr.add(s);
         }
        return arr;
    }

    @Override
    public boolean exist(Categorie t) throws SQLException {
        
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Categorie WHERE `categorie`.`nom` = ?");
        recherche.setString(1, t.getNom());

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
