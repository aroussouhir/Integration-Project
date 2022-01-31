/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pdfTest.qrCode;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class LoginController implements Initializable {

    @FXML
    private ImageView ImV;
    @FXML
    private Button btnEntrer;
    @FXML
    private TextField nomUser;
    @FXML
    private TextField email;
    @FXML
    private Label labelErreur;

    String nomU, e;
    private ImageView imV;
    Image img2 = new Image("login.png");
    
    @FXML
    private ImageView imV2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     imV2.setImage(img2);
    }
    private void init()
    {
        nomU = nomUser.getText();
        e = email.getText();
    }

    @FXML
    private void entrer(ActionEvent event) throws IOException, SQLException {
        
        init();
        
        if(nomU.equals("admin"))
        {       
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Sujets.fxml"));
        Parent root = (Parent) loader.load();
        Scene sceneAjoutSujet = new Scene(root);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
        }
        else{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetsF.fxml"));
        Parent root = (Parent) loader.load();
        
        Scene sceneAjoutSujet = new Scene(root);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
        }
    }
    
}
