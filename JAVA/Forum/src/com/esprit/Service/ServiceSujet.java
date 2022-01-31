/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.Service;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Sujet;
import com.esprit.IService.ISujet;
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
import javafx.scene.control.Button;

/**
 *
 * @author arous souhir
 */
public class ServiceSujet implements ISujet<Sujet>{

    private Connection con;
    private Statement ste;

    public ServiceSujet() {
        con = DataBase.getInstance().getConnection();
    }
        
    public void ajouter(Sujet p) throws SQLException
    {
    PreparedStatement pre=con.prepareStatement("INSERT INTO `forum`.`Sujet` ( `topic`, `question`, `createdAt`, `nbJ`, `valid`, `nomUser`, `emailUser`, `idC` ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? );");
 
    pre.setString(1, p.getTopic());
    pre.setString(2, p.getQuestion());    
    pre.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
    pre.setInt(4, p.getNbJ());
    pre.setInt(5, p.getValid());
    pre.setString(6, p.getNomUser()); 
    pre.setString(7, p.getEmailUser()); 
    pre.setInt(8, p.getIdC().getId());
    pre.executeUpdate();
    }  

    public boolean delete(Sujet p) throws SQLException {
        
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from Sujet");       
        
        while (rs.next()) {                
            if(rs.getInt(1)== p.getId())
            {
                PreparedStatement pre=con.prepareStatement("DELETE FROM `forum`.`Sujet` WHERE `sujet`.`Id` = ?");
                pre.setInt(1, p.getId());
                
                ResultSet rsI=ste.executeQuery("select * from Img");  

                while (rsI.next()) {                
                    if(rsI.getInt("sujet_id")== p.getId())
                    {
                        PreparedStatement preI=con.prepareStatement("DELETE FROM `forum`.`img` WHERE `img`.`sujet_id` = ?");
                        preI.setInt(1, p.getId());
                        preI.executeUpdate();
                        pre.executeUpdate();
                    }
                }
                
                rsI=ste.executeQuery("select * from Commentaire"); 
                while (rsI.next()) {                
                    if(rsI.getInt("sujet_id")== p.getId())
                    {
                        PreparedStatement preI=con.prepareStatement("DELETE FROM `forum`.`commentaire` WHERE `commentaire`.`sujet_id` = ?");
                        preI.setInt(1, p.getId());
                        preI.executeUpdate();
                        pre.executeUpdate(); 
                    }
                }                
                
                pre.executeUpdate();
                return true; 
            }
        }
        return false;
    }

    public boolean update(Sujet t) throws SQLException {
        
        String sql = "UPDATE Sujet SET topic=?, question=? WHERE `sujet`.`Id` = ?";
 
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, t.getTopic());
        statement.setString(2, t.getQuestion());
        

        statement.setInt(3, t.getId());
        
      //  int rowsUpdated = 
                statement.executeUpdate();
    /*    if (rowsUpdated > 0){  
            
         sql = "UPDATE Sujet SET valid=? WHERE `sujet`.`Id` = ?";
 
        statement = con.prepareStatement(sql);
        statement.setInt(1, 0);
        statement.setInt(2, t.getId()); 
        statement.executeUpdate();*/
        return true;
     /*   }
        return false;*/
    }
    
     
    public void show(Sujet t) throws SQLException {
        
        System.out.println(t.toString());   
    }

    public Sujet rechercher(int id) throws SQLException {      
        
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Sujet WHERE `sujet`.`Id` = ?");
        recherche.setInt(1, id);

        ResultSet rs = recherche.executeQuery();
        
            while (rs.next()) {   
                
               String topic=rs.getString("topic");
               String question= rs.getString(3); 
               Date createdAt=rs.getDate("createdAt");
               int nbJ=rs.getInt("nbJ");
               int valid=rs.getInt("valid");
               String nomUser=rs.getString("nomUser");
               String emailUser=rs.getString("emailUser");
               
               ServiceCategorie categorie = new ServiceCategorie();
               Categorie idC = categorie.rechercher(rs.getInt("idC"));
                
               
               Sujet s2 =new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
               return s2;
     }
    return null;
    }


    public List<Sujet> readAll() throws SQLException {
        
    List<Sujet> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from Sujet");
    while (rs.next()) {                
               int id=rs.getInt(1);
               String topic=rs.getString("topic");
               String question=rs.getString(3);   
               Date createdAt=rs.getDate("createdAt");
               int nbJ=rs.getInt("nbJ");
               int valid=rs.getInt("valid");
               String nomUser=rs.getString("nomUser");
               String emailUser=rs.getString("emailUser");
               //int idC=rs.getInt("idC");
               ServiceCategorie categorie = new ServiceCategorie();
               Categorie idC = categorie.rechercher(rs.getInt("idC"));
               
               Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
     arr.add(s);
     }
    return arr;
    }
    
    public List<Sujet> afficher() throws SQLException {
        
        List<Sujet> arr=new ArrayList<>();
        PreparedStatement recherche = con.prepareStatement("select * from Sujet WHERE `sujet`.`valid` =1 ");

        ResultSet rs = recherche.executeQuery();

        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question=rs.getString(3); 
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                  // int idC=rs.getInt("idC");
                   ServiceCategorie categorie = new ServiceCategorie();
                   Categorie idC = categorie.rechercher(rs.getInt("idC"));

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
         arr.add(s);
         }
        return arr;   
    }

    public List<Sujet> demandes() throws SQLException {
        List<Sujet> arr=new ArrayList<>();
        PreparedStatement recherche = con.prepareStatement("select * from Sujet WHERE `sujet`.`valid` =0 ");

        ResultSet rs = recherche.executeQuery();

        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question=rs.getString(3);  
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceCategorie categorie = new ServiceCategorie();
                   Categorie idC = categorie.rechercher(rs.getInt("idC"));

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser,emailUser, idC);
         arr.add(s);
         }
        return arr;  
    }
    

    public void valider(Sujet t) throws SQLException {
        
        t.setValid(1);
        
        String sql = "UPDATE Sujet SET valid=1 WHERE `sujet`.`Id` = ?";
 
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, t.getId());
        statement.executeUpdate();
    }

    public void rejeter(Sujet t) throws SQLException {
        delete(t);
    }

    public ObservableList<Sujet> triNbJ() throws SQLException {
        
        ObservableList<Sujet> arr= FXCollections.observableArrayList();
        PreparedStatement recherche = con.prepareStatement("select * from Sujet WHERE `sujet`.`valid` =1 ORDER BY nbJ desc ");

        ResultSet rs = recherche.executeQuery();

        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question=rs.getString(3);
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceCategorie categorie = new ServiceCategorie();
                   Categorie idC = categorie.rechercher(rs.getInt("idC"));

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
         arr.add(s);
         }
        return arr;           
    }

    @Override
    public ObservableList<Sujet> sujetsUser(String nomUser) throws SQLException {
        
        ObservableList<Sujet> arr= FXCollections.observableArrayList();
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Sujet WHERE `sujet`.`nomUser` = ?");
        recherche.setString(1, nomUser);
        ResultSet rs = recherche.executeQuery();

        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question=rs.getString(3); 
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String emailUser=rs.getString("emailUser");
                   ServiceCategorie categorie = new ServiceCategorie();
                   Categorie idC = categorie.rechercher(rs.getInt("idC"));
                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
         arr.add(s);
         }
        return arr;                 
    }

    @Override
    public int idSujet(String topic) throws SQLException {

        int id = -1;
        ste=con.createStatement();
        ResultSet rs = ste.executeQuery("select id from sujet s where s.topic ='"+topic+"'");
        while (rs.next()){
            id= rs.getInt(1);
        }
        return id;
    }

    @Override
    public ObservableList<Sujet> readAll2() throws SQLException {
        ObservableList<Sujet> arr=FXCollections.observableArrayList();
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from Sujet");
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question=rs.getString(3);   
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceCategorie categorie = new ServiceCategorie();
                   Categorie idC = categorie.rechercher(rs.getInt("idC"));

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
         arr.add(s);
         }
        return arr;
    }

    @Override
    public ObservableList<Sujet> afficher2() throws SQLException {
        ObservableList<Sujet> arr=FXCollections.observableArrayList();
        PreparedStatement recherche = con.prepareStatement("select * from Sujet WHERE `sujet`.`valid` =1 ");

        ResultSet rs = recherche.executeQuery();

        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question=rs.getString(3); 
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceCategorie categorie = new ServiceCategorie();
                   Categorie idC = categorie.rechercher(rs.getInt("idC"));

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
         arr.add(s);
         }
        return arr;   
    }

    @Override
    public ObservableList<Sujet> demandes2() throws SQLException {
        ObservableList<Sujet> arr=FXCollections.observableArrayList();
        PreparedStatement recherche = con.prepareStatement("select * from Sujet WHERE `sujet`.`valid` =0 ");

        ResultSet rs = recherche.executeQuery();

        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question=rs.getString(3);  
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceCategorie categorie = new ServiceCategorie();
                   Categorie idC = categorie.rechercher(rs.getInt("idC"));

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
         arr.add(s);
         }
        return arr;  
    }

    @Override
    public boolean exist(Sujet t) throws SQLException {
        
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Sujet WHERE `sujet`.`topic` = ?");
        recherche.setString(1, t.getTopic());

        ResultSet rs = recherche.executeQuery();

        int x=0;
        while(rs.next())
        {
            x++;
        }
        if(x==0) { return false;}
    return true;
    }

    @Override
    public void ajouterA(Sujet p) throws SQLException {
            PreparedStatement pre=con.prepareStatement("INSERT INTO `forum`.`Sujet` ( `topic`, `question`, `createdAt`, `nbJ`, `valid`, `nomUser`, `emailUser`, `idC` ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? );");
 
    pre.setString(1, p.getTopic());
    pre.setString(2, p.getQuestion());    
    pre.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
    pre.setInt(4, p.getNbJ());
    pre.setInt(5, 1);
    pre.setString(6, p.getNomUser()); 
    pre.setString(7, p.getEmailUser()); 
    pre.setInt(8, p.getIdC().getId());
    pre.executeUpdate();
    }

    @Override
    public void rejeter2(Sujet t) throws SQLException {
        
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Sujet WHERE `sujet`.`id` = ?");
        recherche.setInt(1, t.getId());

        ResultSet rs = recherche.executeQuery();
        
        while(rs.next())
        {
        
        int id=rs.getInt(1);
        String topic=rs.getString("topic");
        String question=rs.getString(3);  
        Date createdAt=rs.getDate("createdAt");
        int nbJ=rs.getInt("nbJ");
        int valid=rs.getInt("valid");
        String nomUser=rs.getString("nomUser");
        String emailUser=rs.getString("emailUser");
        ServiceCategorie categorie = new ServiceCategorie();
        Categorie idC = categorie.rechercher(rs.getInt("idC"));

        Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
        delete(s);
        }
    }

    @Override
    public void Valider2(Sujet t) throws SQLException {

                PreparedStatement recherche = con.prepareStatement("SELECT * FROM Sujet WHERE `sujet`.`id` = ?");
        recherche.setInt(1, t.getId());

        ResultSet rs = recherche.executeQuery();
        
        while(rs.next())
        {
        
        int id=rs.getInt(1);
        String topic=rs.getString("topic");
        String question=rs.getString(3);  
        Date createdAt=rs.getDate("createdAt");
        int nbJ=rs.getInt("nbJ");
        int valid=rs.getInt("valid");
        String nomUser=rs.getString("nomUser");
        String emailUser=rs.getString("emailUser");
        ServiceCategorie categorie = new ServiceCategorie();
        Categorie idC = categorie.rechercher(rs.getInt("idC"));

        Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);
        
        
        s.setValid(1);
        
        String sql = "UPDATE Sujet SET valid=1 WHERE `sujet`.`Id` = ?";
 
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
    }

    @Override
    public ObservableList<Sujet> search(String topic1) throws SQLException {
        String query="SELECT * FROM Sujet Where  topic LIKE '%"+topic1 +"%'" ;
          ObservableList<Sujet> arr= FXCollections.observableArrayList();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery(query);
     while (rs.next()) {      
                   int id=rs.getInt(1);
                   String topic=rs.getString("topic");
                   String question=rs.getString(3);   
                   Date createdAt=rs.getDate("createdAt");
                   int nbJ=rs.getInt("nbJ");
                   int valid=rs.getInt("valid");
                   String nomUser=rs.getString("nomUser");
                   String emailUser=rs.getString("emailUser");
                   ServiceCategorie categorie = new ServiceCategorie();
                   Categorie idC = categorie.rechercher(rs.getInt("idC"));

                   Sujet s=new Sujet(id, topic, question, createdAt, nbJ, valid, nomUser, emailUser, idC);

               System.out.println(s);
        arr.add(s);
     }
          
    return arr;
    }

  
}

