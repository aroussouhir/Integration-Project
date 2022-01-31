/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.Service;

import com.esprit.Entite.Img;
import com.esprit.Entite.Sujet;
import com.esprit.IService.IImg;
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

/**
 *
 * @author arous souhir
 */
public class ServiceImg implements IImg<Img>{
    
    private Connection con;
    private Statement ste;

    public ServiceImg() {
        con = DataBase.getInstance().getConnection();
    }

    
    public void ajouter(Img p) throws SQLException {
        
      PreparedStatement pre=con.prepareStatement("INSERT INTO `forum`.`Img` ( `ref`, `sujet_id`, `com_id`) VALUES ( ?, ?, ?);");
   
     /* File ref= new File(p.getRef());
        FileInputStream fis;
            try {
                fis = new FileInputStream(ref);
                pre.setBinaryStream(1, fis, (int)ref.length());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ServiceSujet.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        pre.setString(1, p.getRef());
        pre.setInt(2, p.getSujet_id());
        pre.setInt(3, p.getCom_id());    
        pre.executeUpdate();
    }

    
    public boolean delete(Img p) throws SQLException {
        
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select * from Img");       
        
        while (rs.next()) {                
            if(rs.getInt(1)== p.getId())
            {
                PreparedStatement pre=con.prepareStatement("DELETE FROM `forum`.`Img` WHERE `img`.`Id` = ?");
                pre.setInt(1, p.getId());
                pre.executeUpdate();
        
                return true; 
            }
        }
        return false;
    }


    public List<Img> imgCom(int com_id) throws SQLException {

        List<Img> arr=new ArrayList<>();
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select * from Img WHERE img.com_id = '"+ com_id +"'");
    
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String ref=rs.getString("ref");
                   int sujet_id=rs.getInt("sujet_id");

                   Img i =new Img(id, ref, sujet_id, com_id);
                   arr.add(i);
         }
        return arr; 
    }


    public List<Img> imgSujet(int sujet_id) throws SQLException {

            List<Img> arr=new ArrayList<>();
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select * from Img WHERE img.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {                
                   int id=rs.getInt(1);
                   String ref=rs.getString("ref");
                   int com_id=rs.getInt("com_id");

                   Img i =new Img(id, ref, sujet_id, com_id);
                   arr.add(i);
        }
        return arr;       
    }


    public void show(Img t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Img rechercher(int id) throws SQLException {
    
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Img WHERE `img`.`Id` = ?");
        recherche.setInt(1, id);

        ResultSet rs = recherche.executeQuery();
        
            while (rs.next()) {   
                
               String ref=rs.getString("ref");
               int sujet_id = rs.getInt("sujet_id");
               int com_id = rs.getInt("com_id");
               
               Img c =new Img(id, ref, sujet_id, com_id);
               return c;
     }
    return null;
    }

    @Override
    public String imSujet1(int sujet_id) throws SQLException {
        String ref="";
        int x=0;
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select ref from Img WHERE img.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {    
            
            if (x==0)
            {
                   ref=rs.getString("ref");
            }
            x++;
        }
        if(x==0){ return "non" ; }
        return ref; 
    }
    
        public Img rechercher2 (int sujet_id) throws SQLException {
    
        PreparedStatement recherche = con.prepareStatement("SELECT * FROM Img WHERE img.sujet_id = '"+ sujet_id +"'");


         ResultSet rs = recherche.executeQuery();
        
            while (rs.next()) {   
               int id = rs.getInt("id");
               String ref=rs.getString("ref");
               int com_id = rs.getInt("com_id");
               
               Img c =new Img(id, ref, sujet_id, com_id);
               return c;
     }
    return null;
     }

    @Override
    public void delete2(int sujet_id) throws SQLException {
        
         PreparedStatement recherche = con.prepareStatement("SELECT * FROM Img WHERE img.sujet_id = '"+ sujet_id +"'");

         ResultSet rs = recherche.executeQuery();
         
         while (rs.next()) {                
                PreparedStatement pre=con.prepareStatement("DELETE FROM `forum`.`Img` WHERE `img`.`Id` = ?");
                pre.setInt(1, rs.getInt("id"));
                pre.executeUpdate();
            }
    }

    @Override
    public String imSujet2(int sujet_id)throws SQLException {
        String ref="";
        int x=0;
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select ref from Img WHERE img.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {    
            
            if (x==1)
            {
                   ref=rs.getString("ref");
            }
            x++;
        }
        if(x==0){ return "non" ; }
        return ref; 
    }

    @Override
    public String imSujet3(int sujet_id) throws SQLException {
        String ref="";
        int x=0;
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select ref from Img WHERE img.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {    
            
            if (x==2)
            {
                ref=rs.getString("ref");
            }
            x++;
        }
        if(x==0){ return "non" ; }
        return ref; 
    }

    @Override
    public int idimSujet1(int sujet_id) throws SQLException {

                int id=0;
        int x=0;
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select id from Img WHERE img.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {    
            
            if (x==0)
            {
                id=rs.getInt("id");
            }
            x++;
        }
        if(x==0){ return 0 ; }
        return id; 
    }

    @Override
    public int idimSujet2(int sujet_id) throws SQLException {
        int id=0;
        int x=0;
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select id from Img WHERE img.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {    
            
            if (x==1)
            {
                id=rs.getInt("id");
            }
            x++;
        }
        if(x==0){ return 0 ; }
        return id; 
    }

    @Override
    public int idimSujet3(int sujet_id) throws SQLException {
        int id=0;
        int x=0;
        ste=con.createStatement();

        ResultSet rs=ste.executeQuery("select id from Img WHERE img.sujet_id = '"+ sujet_id +"'");
    
        while (rs.next()) {    
            
            if (x==2)
            {
                id=rs.getInt("id");
            }
            x++;
        }
        if(x==0){ return 0 ; }
        return id; 
    }

    
}
