/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Sujet;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pdfTest.mailTest;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class EmailController implements Initializable {

    @FXML
    private TextArea mess;
    @FXML
    private Button btnEnvoi;
    @FXML
    private ImageView imvP;
    @FXML
    private Label info;
    
    Image p = new Image("person.png");
    
    String email;
    @FXML
    private Label confirmation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imvP.setImage(p);
    }    
    
    public void export (Sujet s){
        
        info.setText("  "+s.getNomUser()+"\n"+"  "+s.getEmailUser());
        email =s.getEmailUser() ;
            
    }

    @FXML
    private void envoyer(ActionEvent event) {
        
        mailTest mail =new mailTest();
        mail.envoyer(mess.getText(), email);
        
        confirmation.setText(" Message envoyé.");
    }
    
}
