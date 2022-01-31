/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.test;

import com.esprit.Entite.Categorie;
import com.esprit.Service.ServiceCategorie;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceSujet;
import com.esprit.Entite.Commentaire;
import com.esprit.Service.ServiceCommentaire;
import com.esprit.Entite.Img;
import com.esprit.Entite.Likes;
import com.esprit.Entite.Likes_com;
import com.esprit.Service.ServiceImg;
import com.esprit.Service.ServiceLikes;
import com.esprit.Service.ServiceLikesCom;
import com.esprit.Utils.DataBase;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author House
 */
public class Test {
    
    public static void main(String[] args) {
        
        ServiceSujet sujet = new ServiceSujet();
        ServiceCategorie categorie = new ServiceCategorie();
        ServiceCommentaire commentaire = new ServiceCommentaire();
        ServiceImg img = new ServiceImg();
        ServiceLikes likes = new ServiceLikes();
        ServiceLikesCom likesCom = new ServiceLikesCom();
        
        Scanner saisie = new Scanner(System.in);
        
        Categorie cat1 = new Categorie("Autres");
        Categorie cat2 = new Categorie(5,"test"); //existant
        Categorie cat3 = new Categorie(3,"test"); //existant
     
        Sujet s1 = new Sujet(96,"test","test",cat1); //existant
        Sujet s3 = new Sujet(97,"test","test",cat3); //existant
    
        Sujet s2 = new Sujet("houniiiii","quest",cat3);
        
        Img i1 = new Img(5,"img6.jpg",96,0); //existant
        Img i2 = new Img("img5.jpg",103,0);
        Img i3 = new Img("img4.jpeg",0,7);
        
        Commentaire c2 = new Commentaire(3,"testeff",s1);  //existant
        Commentaire c3 = new Commentaire(4,"testeff",s3);  //existant
        
        Commentaire c1 = new Commentaire("TESTATTTT",s1);
        
        try {
     
           //Ajout
        /*    sujet.ajouter(s2);
            
            //Recherche + Show
            System.out.println("Donner l'id d'un sujet :");
            int id = saisie.nextInt();
            
            if(sujet.rechercher(id)!=null)
            {
                sujet.show(sujet.rechercher(id));
            }
            else {
                System.out.println("Sujet introuvable.");
            }
            
            //Modification
            if(sujet.update(s1))
            {
                System.out.println("Modification terminee.");
                sujet.show(s1);
            }
            else{
                System.out.println("Aucune modification effectuee.");
            }

            //Suppression
            if(sujet.delete(s3))
            {
                System.out.println("Element supprime.");
            }
            else{
                System.out.println("Element introuvable.");
            }
           
            //Affichage
            System.out.println(sujet.afficher());
            
            //Demandes a valider
            List<Sujet> list = sujet.demandes();
            String reponse;
            for (Sujet d : list)
            {
                System.out.println("\n"+d+"Valider (v) / Rejeter (r) ?");
                reponse = saisie.next();
                if(reponse.equals("v"))
                {
                    sujet.valider(d);
                }
                if(reponse.equals("r"))
                {
                    sujet.rejeter(d);
                }       
            }
            
            System.out.println(sujet.afficher());
         
            //Affichage avec Tri selon le NbJ
            System.out.println(sujet.triNbJ());  
        
            //Liste des sujets du user
            System.out.println("Donner votre nom :");
            String nomUser = saisie.next();
            
            System.out.println("Vos sujets :");
            
            System.out.println(sujet.sujetsUser("client"));       
        
           //Tous les sujets
            System.out.println(sujet.readAll());  
     
    
            //Categories------------------------------
           categorie.ajouter(cat1);
           categorie.delete(cat2);
            
            if(categorie.update(cat3))
            {
                System.out.println("Modification terminee.");
                categorie.show(cat3);
            }
            else{
                System.out.println("Aucune modification effectuee.");
            }
                      
            categorie.show(categorie.rechercher(2));
            
            if(categorie.delete(cat3))
            {
                System.out.println("Element supprime.");
            }
            else{
                System.out.println("Element introuvable.");
            }

            System.out.println(categorie.readAll());
          
            System.out.println("Donner l'ID d'une categorie :");
            int idC = saisie.nextInt();
            
            System.out.println("Les sujets correspondants :");
            System.out.println(categorie.readSujets(idC));
         
     
         //Commentaires--------------------------------
         
            //Ajout
            commentaire.ajouter(c1);

            //Recherche + Show
            System.out.println("Donner l'id d'un commentaire :");
            int com_id = saisie.nextInt();
            
            if(commentaire.rechercher(com_id)!=null)
            {
                commentaire.show(commentaire.rechercher(com_id));
            }
            else {
                System.out.println("Sujet introuvable.");
            }
            
            //Modification
            if(commentaire.update(c2))
            {
                System.out.println("Modification terminee.");
                commentaire.show(c2);
            }
            else{
                System.out.println("Aucune modification effectuee.");
            }

            //Suppression
            if(commentaire.delete(c3))
            {
                System.out.println("Element supprime.");
            }
            else{
                System.out.println("Element introuvable.");
            }
            
            //Affichage
            System.out.println("Donner l'ID d'un sujet :");
            int sujet_id = saisie.nextInt();
            
            System.out.println("Les commentaires correspondants :");
            System.out.println(commentaire.readCommentaires(sujet_id));

         
            //Affichage avec Tri selon le NbJ
            System.out.println("-- Affichage selon le nbrJ --\n"+commentaire.triNbJ(sujet_id));  
        
            //Liste des sujets du user
            System.out.println("Donner votre nom :");
            nomUser = saisie.next();
            
            System.out.println("Vos commentaires :");
            
            System.out.println(commentaire.commentairesUser(nomUser));     
            
            //IMAGES / FILES --------------------------
            
            img.ajouter(i3);
            img.ajouter(i2);
            
            img.delete(i1);
     
            System.out.println(img.imgCom(7));
            System.out.println(img.imgSujet(103));
     */       
            
            //Like--------------------------------------
          
            Likes_com l1 = new Likes_com(88,"souhir");
            Likes_com l2 = new Likes_com(51,88,"client");
            
          /*  likesCom.ajouter(l1);
            likesCom.ajouter(l2);  */
          //  likesCom.delete(l2);
            
         //   System.out.println("Sujets Favoris <3 \n"+likes.userLikesSujet("souhir"));
           System.out.println("Commentaires Favoris <3 \n"+likesCom.userLikesCommentaire("Souhir"));
          // System.out.println("Chercher \n"+likesCom.rechercher("sarra",199));

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
}
