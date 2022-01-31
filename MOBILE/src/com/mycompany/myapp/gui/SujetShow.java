/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Commentaire;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.services.ServiceCommentaire;
import com.mycompany.myapp.services.ServiceSujet;
import java.io.IOException;

/**
 *
 * @author arous souhir
 */
public class SujetShow extends Form{
    
    Form current;
    public SujetShow(Form previous, Sujet s) {
        current=this;
        
        setTitle("Details du sujet");
        Form f = new Form(s.getQuestion(), new BorderLayout());
        Container hi = new Container(BoxLayout.y());
        hi.setScrollableY(true);
        hi.setScrollableX(true);
        hi.add(new Label("Titre : "+s.getTopic())) ;
        hi.add(new Label("Auteur : "+s.getNomUser())) ;
        Label labelLike=new Label(s.getNbJ()+" J'aime");
//        hi.add("Auteur : ",s.getNomUser());
        hi.add(labelLike) ;
        CheckBox like = new CheckBox("J aime");
        //Label like = new Label("J aime");
        hi.add(like);
        

        
        EncodedImage enc = null ;
    Image imgs;
    ImageViewer imgv = null;

    String url = "http://localhost/mobile/img1.jpg"; //ken theb trecuperi mel server local mte3et
    
               try {
            enc = EncodedImage.create("/load.png"); //image tethat en attendant iloadi 
        } catch (IOException ex) {
        }
        imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
        imgv = new ImageViewer(imgs);
        
        hi.add(imgv);
        
        

          Form f2 = new Form("Tous les commentaires", new BorderLayout());
          int x=0;
          Container list = new Container(BoxLayout.y());
          list.setScrollableY(true);
  
            for ( Commentaire c : ServiceCommentaire.getInstance().sujetCommentaires(s))
            {
                    list.add(c.toString());
                    x=1;
            } 
  
  f2.add(CENTER, list);
  
  TextField contenu = new TextField("", "Ecrire..");
  Label l = new Label("Ajouter un commentaire :");
  Button btnajout = new Button("Ajouter");
  hi.add(l);
  hi.add(contenu);
  hi.add(btnajout);
  
        btnajout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (contenu.getText().length()==0)
                    Dialog.show("Alert", "Champs vide.", new Command("OK"));
                else
                {
                        Commentaire t = new Commentaire(contenu.getText(), "Souhir", s);
                        if( ServiceCommentaire.getInstance().addCommentaire(t))
                        {Dialog.show("Ajout reussi","Votre commentaire a ete ajoute.",new Command("OK"));
                         new SujetShow(current, s).show();
                                
                        }
                        else
                            Dialog.show("Erreur", "Probleme de connexion", new Command("OK"));            
                }               
            }
        });
        
        
        
 /* f2.getContentPane().addPullToRefresh(new Runnable() {
      public void run() {
          f2.removeAll();
          //f2.revalidate();
      }  });*/
        
        f.add(CENTER, hi);
        
        like.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                if (like.isSelected())
                {
                s.setNbJ(s.getNbJ()+1);
                labelLike.setText(s.getNbJ()+" j'aime");
                }
                else {
                                    s.setNbJ(s.getNbJ()-1);
                labelLike.setText(s.getNbJ()+" j'aime");
                }
            }
        });
      
  if(x==1)
  { addAll(f, f2); }
  else if(x==0)
  {  addAll(f); }
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
