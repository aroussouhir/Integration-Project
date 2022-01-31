/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Img;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCategorie;
import com.esprit.Service.ServiceImg;
import com.esprit.Service.ServiceSujet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pdfTest.mailTest;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class AjoutSujetFController implements Initializable {

    @FXML
    private TextArea topic;
    @FXML
    private TextArea question;
    @FXML
    private Button btnAjout;
    @FXML
    private TextField inImg;
    @FXML
    private Button btnImporter;
    @FXML
    private Label confirmation;
    @FXML
    private ComboBox<String> categorie;

    ServiceCategorie sc = new ServiceCategorie();

    String nomU= "Souhir";
    String email = "souhir.arous@esprit.tn";
    @FXML
    private ImageView imv1;
    @FXML
    private ImageView imv2;
    @FXML
    private ImageView imv3;
    Image photo = new Image("img.png");
    
    String im1 = "";
    String im2 = "";
    String im3 = "";
    
    int x =0;
    int y=0;
    int z=0;
    @FXML
    private ImageView imvForum;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imvForum.setImage(new Image("forum.png"));
                try{
        ObservableList<String> ListCateg = sc.readAllNew();
        categorie.setItems(ListCateg);
        }
        catch (SQLException ex){
            System.out.println(ex);
        }
    }    

    @FXML
    private void forum(ActionEvent event) throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("SujetsF.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }

    @FXML
    private void ajout(ActionEvent event)throws SQLException{
        
        String cat = (String) categorie.getSelectionModel().getSelectedItem();
        int id = sc.idCateg(cat); 
        Categorie catt = sc.rechercher(id);
        
        ServiceSujet sujet = new ServiceSujet();
        Sujet s = new Sujet();
        
        s.setTopic(topic.getText());
        s.setQuestion(question.getText());
        s.setIdC(catt);
        
        s.setNomUser(nomU);
        s.setEmailUser(email);
        
        if(sujet.exist(s))
        {
            confirmation.setText("Existe deja");
        }
        else{
        sujet.ajouter(s); 
        confirmation.setText("Ajout reussi ! L'administrateur doit valider votre publication.");
        
        mailTest mail =new mailTest();
        mail.envoyer("Vous avez une nouvelle demande d'ajout de sujet : \n"+"\n" +s.toString2(), email);
        
      /*  if(!inImg.getText().equals(""))
        {
            ServiceImg img = new ServiceImg();
            Img i = new Img(inImg.getText(),sujet.idSujet(topic.getText()),0);
            img.ajouter(i);
        }*/
      if(x==1)
        {
            ServiceImg img = new ServiceImg();
            Img i = new Img(im1,sujet.idSujet(topic.getText()),0);
            img.ajouter(i);
        }
        if(y==1)
                {
            ServiceImg img = new ServiceImg();
            Img i = new Img(im2,sujet.idSujet(topic.getText()),0);
            img.ajouter(i);
                }
        if(z==1)
        {
            ServiceImg img = new ServiceImg();
            Img i = new Img(im3,sujet.idSujet(topic.getText()),0);
            img.ajouter(i);
        }
      
      
        }
    }
    
    FileChooser fileChooser = new FileChooser();
    String url, name;
    
    private void imgWindow(Stage primaryStage){
        FileChooser.ExtensionFilter extFilterJPG =
                new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg =
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG =
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        
        File selectedFile;
 
        selectedFile   = fileChooser.showOpenDialog(primaryStage);
              url = selectedFile.getAbsolutePath();
              File source = new File(url); 
              name = selectedFile.getName();
             
              File dest = new File ("C:\\wamp64\\www\\javafx\\"+name);
        try {
            Files.copy(source.toPath(),dest.toPath());
        } catch (IOException ex) {
            System.out.println(ex);
        }
              System.out.println(url);
    }

    @FXML
    private void ajoutImg(ActionEvent event) {
        
                if(im1.equals(""))
        {
                    Stage primaryStage = null;
        imgWindow(primaryStage);
            im1=name;
            x=1;
            imv1.setImage(photo);
        }
        else if(im2.equals(""))
                {
                            Stage primaryStage = null;
        imgWindow(primaryStage);
                    im2=name;
                    y=1;
                    imv2.setImage(photo);
                }
        else if(im3.equals(""))
        {
                    Stage primaryStage = null;
        imgWindow(primaryStage);
            im3=name;
            z=1;
            imv3.setImage(photo);
        }
        else{
            confirmation.setText("Maximum 3 photos");
        }
        inImg.setText(name);
    }

    @FXML
    private void forum2(MouseEvent event) {
    }


    
}
