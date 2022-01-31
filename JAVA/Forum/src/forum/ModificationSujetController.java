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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pdfTest.mailTest;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class ModificationSujetController implements Initializable {

    @FXML
    private TextField topic;
    @FXML
    private TextArea question;
    @FXML
    private ComboBox<String> categorie;
    
    ServiceCategorie sc = new ServiceCategorie();
    Sujet sI;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnAnnuler;
    @FXML
    private ImageView imV;
    private Button btnSup;
    @FXML
    private TextField img;
    @FXML
    private Button btnImporter;
    
    ServiceImg si = new ServiceImg();

    @FXML
    private ImageView imV3;
    @FXML
    private ImageView imV2;
    
    Image photo = new Image("img.png");
    Image imgSup = new Image("supp.png");
    
    int nb = 0 ;
    Image img3= null;
    Image img2= null;
    Image img1= null;
    @FXML
    private ImageView imvS2;
    @FXML
    private ImageView imvs;
    @FXML
    private ImageView imvS3;
    
    String ref ="";
    String ref2 ="" ;
    String ref3 ="";
    
int x = 0;
int y =0;
int z=0;

int xs = 0;
int ys =0;
int zs=0;

int id1 = 0;
int id2 =0;
int id3 =0;
    @FXML
    private Label confirmation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAnnuler.setStyle("-fx-background-color: #ccccff; ");
        btnSave.setStyle("-fx-background-color: #FFC8A8 ; ");
        btnImporter.setStyle("-fx-background-color: #b3c6ff; ");
        
        imV.setImage(photo);
        imV2.setImage(photo);
        imV3.setImage(photo);
        
        imvs.setImage(imgSup);
        imvS2.setImage(imgSup);
        imvS3.setImage(imgSup);
    }    

    @FXML
    private void enregistrer(ActionEvent event) throws SQLException, IOException {
         
        String cat = (String) categorie.getSelectionModel().getSelectedItem();
        int id = sc.idCateg(cat); 
        Categorie catt = sc.rechercher(id);

        ServiceSujet sujet = new ServiceSujet();
        
        mailTest mail =new mailTest();
      //  mail.envoyer("Bonjour "+sI.getNomUser()+", votre publication a été modifiée par l'administrateur. \n"+"Avant modification - \n"+"Titre : "+sI.getTopic()+"\n"+"Question : "+sI.getQuestion()+"\n"+"Apres modification - \n"+"Titre : "+topic.getText()+"\n"+"Question : "+question.getText()+"\n"+"Merci pour votre comprehension.", sI.getEmailUser());
        mail.envoyer("Bonjour "+sI.getNomUser()+", \n"+"\n"+"Votre publication a été modifiée par l'administrateur. \n"+"\n"+"-Avant modification- \n"+"\n"+"Titre : "+sI.getTopic()+"\n"+"Question : "+sI.getQuestion()+"\n"+"\n"+"-Apres modification- \n"+"\n"+"Titre : "+sI.getTopic()+"\n"+"Question : "+sI.getQuestion()+"\n"+"\n"+"Merci pour votre comprehension.", sI.getEmailUser());
     
        
        sI.setTopic(topic.getText());
        sI.setQuestion(question.getText());
        if(catt != null)
        {
        sI.setIdC(catt);
        }

        sujet.update(sI);  

/*        if(img.getText().equals(""))
        {
            si.delete(si.rechercher2(sI.getId()));
        }
     //   else if (x==1)
        {
            si.delete2(sI.getId());
            Img i = new Img(img.getText(),sI.getId(),0);
            si.ajouter(i);
        }*/
        
        if ( xs ==1 )
        {
            si.delete(si.rechercher(id1));
        }
        if ( ys ==1 )
        {
            si.delete(si.rechercher(id2));
        }
        if ( zs ==1 )
        {
            si.delete(si.rechercher(id3));
        }
        
        if(x==1)
        {
            ServiceImg img = new ServiceImg();
            Img i = new Img(ref,sujet.idSujet(topic.getText()),0);
            img.ajouter(i);
        }
        if(y==1)
                {
            ServiceImg img = new ServiceImg();
            Img i = new Img(ref2,sujet.idSujet(topic.getText()),0);
            img.ajouter(i);
                }
        if(z==1)
        {
            ServiceImg img = new ServiceImg();
            Img i = new Img(ref3,sujet.idSujet(topic.getText()),0);
            img.ajouter(i);
        }
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetShow.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetShowController show = loader.getController();

        show.export(sI);

        Scene sceneAjoutSujet = new Scene(root1);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }
    
    public void export (Sujet s) throws SQLException{
        
        topic.setText(s.getTopic());
        question.setText(s.getQuestion());
        sI=s;
        try{
        ObservableList<String> ListCateg = sc.readAllNew();
        categorie.setItems(ListCateg);
        }
        catch (SQLException ex){
            System.out.println(ex);
        }    
        
        ref = si.imSujet1(s.getId());
        ref2 = si.imSujet2(s.getId());
        ref3 = si.imSujet3(s.getId());
        
        if (ref != "non" )
        {    
            nb =1 ;
            id1 = si.idimSujet1(s.getId());
            try {
                img1 = new Image(new FileInputStream("C:\\wamp64\\www\\mobile\\"+ref));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SujetShowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (ref2 != "" )
            {
                try {
                    img2 = new Image(new FileInputStream("C:\\wamp64\\www\\mobile\\"+ref2));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SujetShowController.class.getName()).log(Level.SEVERE, null, ex);
                }
                nb =2;
                id2 = si.idimSujet2(s.getId());
                
                if (ref3 != "" )
                {
                    try {
                        img3 = new Image(new FileInputStream("C:\\wamp64\\www\\mobile\\"+ref3));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SujetShowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    nb =3;
                    id3 = si.idimSujet3(s.getId());
                }
            }
            
        }
        
        if(img1 == null)
        {
            imV.setVisible(false);
            imvs.setVisible(false);
        }
        if(img2 == null)
        {
            imV2.setVisible(false);
            imvS2.setVisible(false);
        }
        if(img3 == null)
        {
            imV3.setVisible(false);
            imvS3.setVisible(false);
        }
    }

    @FXML
    private void retour(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetShow.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetShowController show = loader.getController();

        show.export(sI);

        Scene sceneAjoutSujet = new Scene(root1);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
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
    private void importer(ActionEvent event) {
        
        if (nb ==3 )
        {
           confirmation.setText("Maximum 3 photos");
        }
        else
        {
        Stage primaryStage = null;
        imgWindow(primaryStage);
        img.setText(name);
        
        if(ref.equals(""))
        {
            ref=name;
            nb++;
            x =1 ;
            imV.setVisible(true);
            imvs.setVisible(true);
        }
        else if(ref2.equals(""))
                {
                    ref2=name;
                    nb++;
                    imV2.setVisible(true);
                    imvS2.setVisible(true);
                    y = 1;
                }
        else if(ref3.equals(""))
        {
            ref3=name;
            nb++;
            imV3.setVisible(true);
            imvS3.setVisible(true);
            z = 1;
        }
        }
    }

    @FXML
    private void voir1(MouseEvent event) throws IOException {
        
        if(nb >0 )
        {
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VoirImg.fxml"));
        Parent root1 = (Parent) loader.load();

        VoirImgController show = loader.getController();

        show.export(img1, null, null, 1);

        Scene scene2 = new Scene(root1);
        Stage anotherStage = new Stage();

        anotherStage.setScene(scene2);
        anotherStage.show();
        }
    }

    @FXML
    private void voir3(MouseEvent event) throws IOException {
        
        if(nb >2 )
        {
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VoirImg.fxml"));
        Parent root1 = (Parent) loader.load();

        VoirImgController show = loader.getController();
        
        show.export(img3, null, null, 1);

        Scene scene2 = new Scene(root1);
        Stage anotherStage = new Stage();

        anotherStage.setScene(scene2);
        anotherStage.show();
        }
    }

    @FXML
    private void voir2(MouseEvent event) throws IOException {
        
        if(nb >1 )
        {
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VoirImg.fxml"));
        Parent root1 = (Parent) loader.load();

        VoirImgController show = loader.getController();
        
        show.export(img2, null, null, 1);

        Scene scene2 = new Scene(root1);
        Stage anotherStage = new Stage();

        anotherStage.setScene(scene2);
        anotherStage.show();
        }
    }

    @FXML
    private void supp2(MouseEvent event) {
 
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Supprimer cette publication ?");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    System.out.println("ok");
                }else {
                    nb--;
                    ys = 1;
                 imV2.setVisible(false);
                 imvS2.setVisible(false); 
               }    
    });
    }

    @FXML
    private void supp(MouseEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Supprimer cette publication ?");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    System.out.println("ok");
                }else {
                    nb--;
                    xs = 1;
                   imV.setVisible(false);
                   imvs.setVisible(false);
                   
                   System.out.println();
               }
    });
    }

    @FXML
    private void supp3(MouseEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Supprimer cette publication ?");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    System.out.println("ok");
                }else {
                    zs = 1;
                    nb--;
                   imV3.setVisible(false);
                   imvS3.setVisible(false);
               }
    });
    }
    
}
