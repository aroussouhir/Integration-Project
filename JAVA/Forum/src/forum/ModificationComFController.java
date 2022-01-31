/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCommentaire;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class ModificationComFController implements Initializable {

    @FXML
    private ImageView imV;
    @FXML
    private TextArea contenu;
    
    Sujet s;
    Commentaire cI;
    ServiceCommentaire sc = new ServiceCommentaire();
    @FXML
    private ImageView imvSupp;
    
    Image imS = new Image("supp.png");
    
    int x;
    @FXML
    private Button btnR;
    @FXML
    private ImageView imvForum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imvSupp.setImage(imS);
        imvForum.setImage(new Image("forum.png"));
        Tooltip.install(imvSupp, new Tooltip("Supprimer le commentaire"));
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
    private void enregistrer(ActionEvent event) throws SQLException, IOException {
        
        cI.setContenu(contenu.getText());

        sc.update(cI);   
        
        
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
    public void export (Commentaire c, int v){
        
        x= v ;
        
        contenu.setText(c.getContenu());
        cI=c;
        s=c.getSujet_id();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException, SQLException {
        if(x==0)
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
                                   try {
                            FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetsF2.fxml"));
                            Parent root1 = (Parent) loader.load();
                            
                            SujetsF2Controller show = loader.getController();
                            
                            show.export(0);
                            
                            Scene sceneAjoutSujet = new Scene(root1);
                            Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            app_Stage.hide();
                            app_Stage.setScene(sceneAjoutSujet);      
                            app_Stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(ModificationComFController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(ModificationComFController.class.getName()).log(Level.SEVERE, null, ex);
                        } 
        }
    }

    @FXML
    private void supprimer(MouseEvent event) throws SQLException, IOException {

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
                        sc.delete(cI);
                    } catch (SQLException ex) {
                        Logger.getLogger(SujetShowFController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    if( x==0 )
                    {
                    try {
                        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetShowF.fxml"));
                         Parent root1 = (Parent) loader.load();

                        SujetShowFController show = loader.getController();

                        show.export(s);

                        Scene sceneAjoutSujet = new Scene(root1);
                        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        app_Stage.hide(); 
                        app_Stage.setScene(sceneAjoutSujet);
                        app_Stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SujetShowFController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      catch (SQLException ex) {
                        Logger.getLogger(ModificationComFController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                    else
                    {                        
                        try {
                            FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetsF2.fxml"));
                            Parent root1 = (Parent) loader.load();
                            
                            SujetsF2Controller show = loader.getController();
                            
                            show.export(0);
                            
                            Scene sceneAjoutSujet = new Scene(root1);
                            Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            app_Stage.hide();
                            app_Stage.setScene(sceneAjoutSujet);      
                            app_Stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(ModificationComFController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(ModificationComFController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            });
    }
    
}
