/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCategorie;
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
import javafx.scene.control.TextField;
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
public class SujetsFController implements Initializable {

    @FXML
    private ListView<Sujet> table;
    
    ObservableList<Sujet> sujets ;
    ServiceSujet ss = new ServiceSujet();
    @FXML
    private ComboBox<String> categorie;
    ServiceCategorie sc = new ServiceCategorie();
    @FXML
    private Pagination pagination;
    private final static int dataSize = 100;
    private final static int rowsPerPage = 8;
    @FXML
    private ComboBox<String> liste;
    
    ServiceLikes sl = new ServiceLikes();
    String nomU ="Souhir";
    @FXML
    private ImageView imvTri;
    @FXML
    private ImageView imvMobile;

    Image imT = new Image("tri.png");
    Image imM = new Image("mobile.png");
    
    int x = 0 ;
    @FXML
    private ImageView imvForum;
    @FXML
    private ImageView imvAjout;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private ImageView imvSearch;
    @FXML
    private ImageView imvLog;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imvTri.setImage(imT);
        imvMobile.setImage(imM);
        imvForum.setImage(new Image("forum.png"));
        imvAjout.setImage(new Image("add.png"));
        imvSearch.setImage(new Image("search.png"));
        imvLog.setImage(new Image("logout.png"));
        Tooltip.install(imvLog, new Tooltip("Deconnexion."));
        Tooltip.install(imvAjout, new Tooltip("Ajouter"));
        Tooltip.install(imvForum, new Tooltip("Acceuil"));
        Tooltip.install(imvMobile, new Tooltip("Telecharger notre application mobile."));
        try { 
            ObservableList<String> ListCateg = sc.readAllNew();
            ListCateg.add("Toutes");
            categorie.setItems(ListCateg);
            
            ObservableList<String> l = FXCollections.observableArrayList();
            l.add("Mes sujets");
            l.add("Mes sujets favoris");
            l.add("Mes commentaires");
            l.add("Mes commentaires favoris");
            
            liste.setItems(l);
        
            sujets = ss.afficher2();

           pagination.setPageFactory(this::createPage);
           
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
        
    private Node createPage(int pageIndex)
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, sujets.size());
        table.setItems(FXCollections.observableArrayList(sujets.subList(fromIndex, toIndex)));
        return table;
    }

    @FXML
    private void voir(MouseEvent event) throws IOException, SQLException {

        ReadOnlyObjectProperty<Sujet> topic = table.getSelectionModel().selectedItemProperty();
        Sujet data = topic.getValue();
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetShowF.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetShowFController show = loader.getController();

        show.export(data);

        Scene sceneAjoutSujet = new Scene(root1);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }

    @FXML
    private void cat(ActionEvent event) throws SQLException {
        
        String cat = (String) categorie.getSelectionModel().getSelectedItem();
        if(cat == "Toutes")
        {
           sujets = ss.afficher2();
           table.setItems(sujets);
        }
        else{
            int id = sc.idCateg(cat); 
            Categorie catt = sc.rechercher(id); 
            sujets = sc.readSujets2(catt.getId());
            table.setItems(sujets);
        }
    }

    ServiceLikesCom lc = new ServiceLikesCom();
    
    @FXML
    private void VoirListe(ActionEvent event) throws SQLException, IOException {
        
        String l = (String) liste.getSelectionModel().getSelectedItem();
        if(l == "Mes sujets")
        {
           sujets = ss.sujetsUser(nomU);
           table.setItems(sujets);
        }
        else if (l == "Mes sujets favoris"){
           sujets = sl.userLikesSujet(nomU);
           table.setItems(sujets);
        }
        else if (l == "Mes commentaires"){

           x=0;
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetsF2.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetsF2Controller show = loader.getController();

        show.export(x);
        
        Scene sceneAjoutSujet = new Scene(root1);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();      
        }
        else if (l == "Mes commentaires favoris"){

           x=1;
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetsF2.fxml"));
        Parent root1 = (Parent) loader.load();

        SujetsF2Controller show = loader.getController();

        show.export(x);

        Scene sceneAjoutSujet = new Scene(root1);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();  
        }
    }

    @FXML
    private void meilleurs(ActionEvent event) throws SQLException {
        
        ObservableList<Sujet> l1 = ss.triNbJ();
        table.setItems(l1);
    }

    @FXML
    private void appMobile(MouseEvent event) {
        Stage ps = new Stage();
        qrCode q = new qrCode();
        q.start(ps, "www.google.com");
    }

    @FXML
    private void accueil(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetsF.fxml"));
        Parent root = (Parent) loader.load();
        
        Scene sceneAjoutSujet = new Scene(root);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }

    @FXML
    private void ajouter2(MouseEvent event) throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("AjoutSujetF.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }

    @FXML
    private void search(ActionEvent event) {
        try {
            table.setItems(ss.search(textFieldSearch.getText())); 
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void search2(MouseEvent event) {
        try {
            table.setItems(ss.search(textFieldSearch.getText())); 
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void logout(MouseEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = (Parent) loader.load();
        
        Scene sceneAjoutSujet = new Scene(root);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }
}
