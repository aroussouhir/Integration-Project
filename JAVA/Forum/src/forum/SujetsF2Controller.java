/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import com.esprit.Entite.Categorie;
import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCategorie;
import com.esprit.Service.ServiceCommentaire;
import com.esprit.Service.ServiceLikes;
import com.esprit.Service.ServiceLikesCom;
import com.esprit.Service.ServiceSujet;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pdfTest.qrCode;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class SujetsF2Controller implements Initializable {

    @FXML
    private ListView<Commentaire> table;
    ObservableList<Commentaire> coms ;
    ServiceSujet ss = new ServiceSujet();
    ServiceCommentaire cc = new ServiceCommentaire();
    @FXML
    private ImageView imvForum;
    
    ServiceCategorie sc = new ServiceCategorie();
    @FXML
    private Pagination pagination;
    private final static int dataSize = 100;
    private final static int rowsPerPage = 8;
    @FXML
    private ImageView imvMobile;
    Image imM = new Image("mobile.png");
    
    ServiceLikesCom lc = new ServiceLikesCom();
    String nomU ="Souhir";
    int y;
    int x;
    @FXML
    private ComboBox<String> liste;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imvMobile.setImage(imM);
        imvForum.setImage(new Image("forum.png"));
        Tooltip.install(imvForum, new Tooltip("Acceuil"));
        Tooltip.install(imvMobile, new Tooltip("Telecharger notre application mobile."));
        ObservableList<String> l = FXCollections.observableArrayList();
        l.add("Mes commentaires");
        l.add("Mes commentaires favoris");
        liste.setItems(l);
    }  
    
    public void export (int s) throws SQLException{

        x = s;
        if( s == 0)
        {
            coms = cc.commentairesUser(nomU);
            y=0;
        }
        else
        {
            coms = lc.userLikesCommentaire("Souhir");
            y=1;
        }
        pagination.setPageFactory(this::createPage);
    }
    
        private Node createPage(int pageIndex)
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, coms.size());
        table.setItems(FXCollections.observableArrayList(coms.subList(fromIndex, toIndex)));
        return table;
    }

    @FXML
    private void voir(MouseEvent event) throws IOException, SQLException{
       ReadOnlyObjectProperty<Commentaire> contenu = table.getSelectionModel().selectedItemProperty();
       Commentaire data = contenu.getValue();
      if ( y==0 )
      {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("ModificationComF.fxml"));
        Parent root1 = (Parent) loader.load();

        ModificationComFController show = loader.getController();

        show.export(data, 1);

        Scene sceneAjoutSujet = new Scene(root1);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
      }
      else
      {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("CommentaireShowF.fxml"));
        Parent root1 = (Parent) loader.load();

        CommentaireShowFController show = loader.getController();

        show.export(data, 1);

        Scene sceneAjoutSujet = new Scene(root1);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
      }
    }

    @FXML
    private void accueil(MouseEvent event)  throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetsF.fxml"));
        Parent root = (Parent) loader.load();
        
        Scene sceneAjoutSujet = new Scene(root);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }

    @FXML
    private void appMobile(MouseEvent event) {
        Stage ps = new Stage();
        qrCode q = new qrCode();
        q.start(ps, "www.google.com");
    }

    
    @FXML
    private void voirListe(ActionEvent event) throws SQLException {
        String l = (String) liste.getSelectionModel().getSelectedItem();
        if(l == "Mes commentaires")
        {
           coms = cc.commentairesUser(nomU);
           table.setItems(coms);
           y=0;
        }
        else if(l == "Mes commentaires favoris")
        {
           coms = lc.userLikesCommentaire("Souhir");
           table.setItems(coms);
           y=1;
}
    }
    
}
