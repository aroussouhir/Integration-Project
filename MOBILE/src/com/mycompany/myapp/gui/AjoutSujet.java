/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.capture.Capture;
import com.codename1.components.ImageViewer;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.mycompany.myapp.entities.Sujet;
import com.mycompany.myapp.services.ServiceImg;
import com.mycompany.myapp.services.ServiceSujet;
import java.io.IOException;

/**
 *
 * @author arous souhir
 */
public class AjoutSujet extends Form{
    
     public AjoutSujet(Form previous) {
        setTitle("Ajouter un sujet");
        setLayout(BoxLayout.y());
        
        TextField tfTopic = new TextField("","Titre");
        TextField tfQuestion= new TextField("", "Question");
        Button btnImg = new Button("Ajouter image");
        Button btnValider = new Button("Ajouter");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfTopic.getText().length()==0)||(tfQuestion.getText().length()==0))
                    Dialog.show("Alert", "Champs vide.", new Command("OK"));
                else
                {
                        Sujet t = new Sujet(tfTopic.getText(), tfQuestion.getText());


                            
                            
                        if( ServiceSujet.getInstance().addSujet(t))
                        { 
                            Sujet s = ServiceSujet.getInstance().chercher(t.getTopic());
                            System.out.println("sujeet" );
                            System.out.println(s);
                            ServiceImg.getInstance().addImg("18922.jpg", s.getId());
                            Dialog.show("Ajout reussi","L'administrateur doit valider votre publication.",new Command("OK"));
                        }else
                        { Dialog.show("Erreur", "Probleme de connexion", new Command("OK")); }
                }               
            }
        });
        
        ImageViewer l = new ImageViewer();
        
        btnImg.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
               String path = Capture.capturePhoto();
               System.out.println(path);
            if (path == null) {
                showToast("User canceled Camera");
                return;
            }
            setImage(path, l);
            }
                private void showToast(String text) {
        Image errorImage = FontImage.createMaterial(FontImage.MATERIAL_ERROR, UIManager.getInstance().getComponentStyle("Title"), 4);
        ToastBar.Status status = ToastBar.getInstance().createStatus();
        status.setMessage(text);
        status.setIcon(errorImage);
        status.setExpires(2000);
        status.show();
    }
                
            
        private void setImage(String filePath, ImageViewer iv) {
        try {
            Image i1 = Image.createImage(filePath);
            iv.setImage(i1);
            iv.getParent().revalidate();
        } catch (Exception ex) {
            Log.e(ex);
            Dialog.show("Error", "Error during image loading: " + ex, "OK", null);
        }
    }
            
                private Image roundImage(Image img) {
        int width = img.getWidth();
        Image roundMask = Image.createImage(width, width, 0xff000000);
        Graphics gr = roundMask.getGraphics();
        gr.setColor(0xffffff);
        gr.fillArc(0, 0, width, width, 0, 360);
        Object mask = roundMask.createMask();
        img = img.applyMask(mask);
        return img;
    }
                private Image userPicture;

    private Image captureRoundImage() {
        try {
            int width = userPicture.getWidth();
            String result = Capture.capturePhoto(width, -1);
            if (result == null) {
                return userPicture;
            }
            Image capturedImage = Image.createImage(result);
            if (capturedImage.getHeight() != width) {
                if (capturedImage.getWidth() < capturedImage.getHeight()) {
                    capturedImage = capturedImage.subImage(0, capturedImage.getHeight() / 2 - width / 2, width, width, false);
                } else {
                    Image n = Image.createImage(width, width);
                    n.getGraphics().drawImage(capturedImage, 0, width / 2 - capturedImage.getHeight() / 2);
                    capturedImage = n;
                }
            }
            return roundImage(capturedImage);
        } catch (IOException err) {
            err.printStackTrace();
            return userPicture;
        }
    }
            
        });

        addAll(tfTopic,tfQuestion,btnImg, btnValider, l);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
    
}
