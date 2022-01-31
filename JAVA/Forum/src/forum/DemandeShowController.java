/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceImg;
import com.esprit.Service.ServiceSujet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pdfTest.mailTest;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class DemandeShowController implements Initializable {

    @FXML
    private ImageView imV;
    @FXML
    private Label question;
    @FXML
    private Label topic;
    Image img = new Image("LOGO.png");
    @FXML
    private ImageView imS;
    
     Sujet s2;
     
    String email ="souhir.arous@esprit.tn";

    @FXML
    private ImageView imgSupp;
    @FXML
    private ImageView imgV;

    Image iS = new Image("rejet.png");
    Image iv = new Image("valid.png");
    
    int onglet = 0 ;
    Image img3;
    Image img2;
    @FXML
    private Button btnR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imV.setImage(img);
        btnR.setStyle("-fx-background-color: #ccccff; ");
        imgSupp.setImage(iS);
        imgV.setImage(iv);
        Tooltip.install(imgSupp, new Tooltip("Rejeter"));
        Tooltip.install(imgV, new Tooltip("Valider"));
    }   
    
    public void export (Sujet s) throws SQLException{
        
        s2 = s ;
        topic.setText(s.getTopic()+"\nAjoute le "+s.getCreatedAt()+" par "+s.getNomUser());
        question.setText(s.getQuestion());
        
        ServiceImg si = new ServiceImg();
        
        String ref = si.imSujet1(s.getId());
                String ref2 = si.imSujet2(s.getId());
        String ref3 = si.imSujet3(s.getId());
        
        if (ref != "non" )
        {    
            onglet =1 ;
            Image img1;
            try {
                img1 = new Image(new FileInputStream("C:\\wamp64\\www\\mobile\\"+ref));
                imS.setImage(img1);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SujetShowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("image 1");
            
            if (ref2 != "" )
            {
                try {
                    img2 = new Image(new FileInputStream("C:\\wamp64\\www\\mobile\\"+ref2));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SujetShowController.class.getName()).log(Level.SEVERE, null, ex);
                }
                onglet =2;
                
                if (ref3 != "" )
                {
                    try {
                        img3 = new Image(new FileInputStream("C:\\wamp64\\www\\mobile\\"+ref3));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SujetShowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    onglet =3;
                }
            }
            
        }
    }

    @FXML
    private void voirImg(MouseEvent event) throws IOException {
     if(onglet ==1 )
        {
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("VoirImg.fxml"));
        Parent root1 = (Parent) loader.load();

        VoirImgController show = loader.getController();
        
        Image m = imS.getImage();

        show.export(m, null, null, onglet);

        Scene scene2 = new Scene(root1);
        Stage anotherStage = new Stage();

        anotherStage.setScene(scene2);
        anotherStage.show();
        }
        
        else if(onglet ==2 )
            {

            FXMLLoader loader= new FXMLLoader(getClass().getResource("VoirImg.fxml"));
            Parent root1 = (Parent) loader.load();

            VoirImgController show = loader.getController();

            Image m = imS.getImage();

            show.export(m, img2, null, onglet);

            Scene scene2 = new Scene(root1);
            Stage anotherStage = new Stage();

            anotherStage.setScene(scene2);
            anotherStage.show();
            }
        
            else if(onglet ==3 )
                {
                FXMLLoader loader= new FXMLLoader(getClass().getResource("VoirImg.fxml"));
                Parent root1 = (Parent) loader.load();

                VoirImgController show = loader.getController();

                Image m = imS.getImage();

                show.export(m, img2, img3, onglet);

                Scene scene2 = new Scene(root1);
                Stage anotherStage = new Stage();

                anotherStage.setScene(scene2);
                anotherStage.show();
                }
    }
    

    ServiceSujet ss = new ServiceSujet();

    @FXML
    private void supp(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Supprimer cette publication ?");
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.YES);
        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    System.out.println("ok");
                }else {
                    try {
                        ss.rejeter2(s2);
                    } catch (SQLException ex) {
                        Logger.getLogger(DemandeShowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Parent pageParent;
                    try {
                    pageParent = FXMLLoader.load(getClass().getResource("Demandes.fxml"));
                    Scene sceneAjoutSujet = new Scene(pageParent);
                    sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
                    Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    app_Stage.hide(); 
                    app_Stage.setScene(sceneAjoutSujet);
                    app_Stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(DemandeShowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
        });
    }

    @FXML
    private void valider(MouseEvent event) throws SQLException {
        
        ss.Valider2(s2);
         email = s2.getEmailUser();
        mailTest mail =new mailTest();
        mail.envoyer("Bonjour "+s2.getNomUser()+", \n"+"\n"+"Votre publication a été validée : \n"+"\n"+ s2.toString2(), s2.getEmailUser());
        Parent pageParent;
        try {
        pageParent = FXMLLoader.load(getClass().getResource("Demandes.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
        } catch (IOException ex) {
            Logger.getLogger(DemandeShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void RetourF(ActionEvent event) throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("Demandes.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();    
    }
    
}
