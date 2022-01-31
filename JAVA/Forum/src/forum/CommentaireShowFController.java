/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Likes;
import com.esprit.Entite.Likes_com;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCommentaire;
import com.esprit.Service.ServiceLikesCom;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class CommentaireShowFController implements Initializable {

    @FXML
    private ImageView imV;
    @FXML
    private Button btnForum;
    @FXML
    private Button btnR;
    @FXML
    private ImageView heart;
    @FXML
    private Label contenu;
    @FXML
    private Label like;
    @FXML
    private Label Info;
    
    Commentaire cI;
    Sujet s ;
    int x ;
    
    Image im1 =new Image("heart.png");
    Image im2 = new Image("heart2.png");
    
    String nomU = "Souhir";
    
    ServiceCommentaire sc = new ServiceCommentaire();
    ServiceLikesCom lc = new ServiceLikesCom();
    @FXML
    private ImageView imvForum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imvForum.setImage(new Image("forum.png"));
    }  
    
    public void export (Commentaire c, int v) throws SQLException{
        
        x= v ;

        Info.setText("Ajouté le : "+c.getCreatedAt()+"   Par : "+c.getNomUser()+"\n"+c.getNbJ()+" J'aime");
        contenu.setText(c.getContenu());
        cI=c;
        s=c.getSujet_id();
        
        if ( lc.rechercher(nomU, c.getId()) != null )
        {
            like.setText("J'aime plus");
            heart.setImage(new Image("heart.png"));
        }
        else 
        {
            heart.setImage(new Image("heart2.png"));
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
    private void retour(ActionEvent event) throws IOException, SQLException {
        if (x==0)
        {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetShowF.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetShowFController show = loader.getController();

        show.export(s);

        Scene sceneAjoutSujet = new Scene(root1);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
        }
        else
        {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetsF2.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetsF2Controller show = loader.getController();

        show.export(1);

        Scene sceneAjoutSujet = new Scene(root1);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show(); 
        }
    }

    @FXML
    private void like(MouseEvent event) throws SQLException {
        
        if (like.getText().equals("J'aime"))
        {
            Likes_com l = new Likes_com(cI.getId(), nomU);
            lc.ajouter(l);
            like.setText("J'aime plus");
            heart.setImage(new Image("heart.png"));
            cI.setNbJ(cI.getNbJ()+1);
            
            if(x==0)
            {
                export(cI, 0);
            }
            else { export(cI, 1); }
            
        }
        else if (like.getText().equals("J'aime plus"))
        {
            like.setText("J'aime");
            heart.setImage(new Image("heart2.png"));
            Likes_com l2 = lc.rechercher(nomU, cI.getId());
            lc.delete(l2);
            cI.setNbJ(cI.getNbJ()-1);
            if(x==0)
            {
                export(cI, 0);
            }
            else { export(cI, 1); }
        }
    }
    
}
