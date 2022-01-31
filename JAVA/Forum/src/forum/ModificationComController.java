/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCommentaire;
import com.esprit.Service.ServiceSujet;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pdfTest.mailTest;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class ModificationComController implements Initializable {

    @FXML
    private TextArea contenu;
    
    Sujet s;
    Commentaire cI;
    @FXML
    private ImageView imV;
    Image img = new Image("LOGO.png");
    @FXML
    private Button btnSave;
    @FXML
    private Button btnR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnR.setStyle("-fx-background-color: #ccccff; ");
        btnSave.setStyle("-fx-background-color: #FFC8A8  ; ");
        imV.setImage(img);
    }    

    public void export (Commentaire c){
        
        contenu.setText(c.getContenu());
        cI=c;
        System.out.println(c.getEmailUser());
        s=c.getSujet_id();
        
    
    }
        
    @FXML
    private void enregistrer(ActionEvent event) throws SQLException, IOException {

        ServiceCommentaire sc = new ServiceCommentaire();
        
        mailTest mail =new mailTest();
        mail.envoyer("Bonjour "+cI.getNomUser()+", \n"+"\n"+"Votre publication a été modifiée par l'administrateur. \n"+"\n"+"-Avant modification- \n"+"\n"+cI.getContenu()+"\n"+"\n"+"-Apres modification- \n"+"\n"+contenu.getText()+"\n"+"\n"+"Merci pour votre comprehension.", cI.getEmailUser());
       
       cI.setContenu(contenu.getText());

        sc.update(cI);    
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetShow.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetShowController show = loader.getController();

        show.export(s);

        Scene sceneAjoutSujet = new Scene(root1);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetShow.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetShowController show = loader.getController();

        show.export(s);

        Scene sceneAjoutSujet = new Scene(root1);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }
    
}
