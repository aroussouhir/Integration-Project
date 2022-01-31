/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Likes;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCommentaire;
import com.esprit.Service.ServiceImg;
import com.esprit.Service.ServiceLikes;
import com.esprit.Service.ServiceSujet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
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
public class SujetShowFController implements Initializable {

    @FXML
    private Label topic;
    @FXML
    private Label question;
    @FXML
    private ListView<Commentaire> table;
    @FXML
    private Pagination pagination;
    ServiceCommentaire sc = new ServiceCommentaire();
    
    ServiceSujet ss = new ServiceSujet();
    @FXML
    private ImageView imS;
    @FXML
    private TextArea NewCom;
    
    Sujet s2;
    private final static int dataSize = 100;
    private final static int rowsPerPage = 4;

    ObservableList<Commentaire> commentaires;
    @FXML
    private Label like;
    
    String nomU= "Souhir";
    String email ="souhir.arous@esprit.tn";
    ServiceLikes sl = new ServiceLikes();
    @FXML
    private Label confirmation;
    @FXML
    private ImageView heart;
    
    Image im1 =new Image("heart.png");
    Image im2 = new Image("heart2.png");
    @FXML
    private ImageView imvSupp;
    
    int onglet = 0 ;
    Image img3;
    Image img2;
    @FXML
    private ImageView imvTri;
    @FXML
    private ImageView imvForum;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        imvSupp.setImage(new Image("supp.png"));
        imvTri.setImage(new Image("tri.png"));
        imvForum.setImage(new Image("forum.png"));
    }  
    
    public void export (Sujet s) throws SQLException{
        
        s2 = s ;
        topic.setText(s.getTopic()+"\nAjoute le "+s.getCreatedAt()+" par "+s.getNomUser()+ "\n" + s.getNbJ()+" J'aime");
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
        commentaires = sc.readCommentaires2(s.getId());
        pagination.setPageFactory(this::createPage);

        if ( sl.rechercher(nomU, s.getId()) != null )
        {
            like.setText("J'aime plus");
            heart.setImage(new Image("heart.png"));
        }
        else 
        {
            heart.setImage(new Image("heart2.png"));
        }
        
        if((!s.getNomUser().equals(nomU))||(!s.getNomUser().equals("souhir")))
        {
             imvSupp.setVisible(false);
        }    
       
    }
        
    private Node createPage(int pageIndex)
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, commentaires.size());
        table.setItems(FXCollections.observableArrayList(commentaires.subList(fromIndex, toIndex)));
        return table;
    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {
        
        Commentaire c = new Commentaire(NewCom.getText(),s2, nomU,email);
        if(NewCom.getText().equals(""))
        {
            confirmation.setText("Champs vide.");
        }
        else if(sc.exist(c))
        {
            confirmation.setText("Existe deja");
        }
        else{
        sc.ajouter(c);
        confirmation.setText("");
        mailTest mail =new mailTest();
        mail.envoyer("Vous avez un nouveau commentaire : \n"+ c.toString(), s2.getEmailUser());
        }
        export(s2);
    }

    @FXML
    private void meilleurs(ActionEvent event) throws SQLException {
        
        ObservableList<Commentaire> l1 = sc.triNbJ(s2.getId());
        table.setItems(l1);
    }

    @FXML
    private void forum(ActionEvent event)throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("SujetsF.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }

    @FXML
    private void VoirImage(MouseEvent event) throws IOException {

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

    @FXML
    private void voir(MouseEvent event)  throws IOException, SQLException {
                  
       ReadOnlyObjectProperty<Commentaire> contenu = table.getSelectionModel().selectedItemProperty();
       Commentaire data = contenu.getValue();
      if ( data.getNomUser().equals(nomU) )
      {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("ModificationComF.fxml"));
        Parent root1 = (Parent) loader.load();

        ModificationComFController show = loader.getController();

        show.export(data, 0);

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

        show.export(data, 0);

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
            Likes l = new Likes(s2.getId(), nomU);
            sl.ajouter(l);
            like.setText("J'aime plus");
            heart.setImage(new Image("heart.png"));
            s2.setNbJ(s2.getNbJ()+1);
            export(s2);
        }
        else if (like.getText().equals("J'aime plus"))
        {
            like.setText("J'aime");
            heart.setImage(new Image("heart2.png"));
            Likes l2 = sl.rechercher(nomU, s2.getId());
            sl.delete(l2);
            s2.setNbJ(s2.getNbJ()-1);
            export(s2);
        }
    }

    @FXML
    private void supprimer2(MouseEvent event)throws SQLException, IOException {

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
                        ss.delete(s2);
                    } catch (SQLException ex) {
                        Logger.getLogger(SujetShowFController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Parent pageParent;
                    try {
                        pageParent = FXMLLoader.load(getClass().getResource("SujetsF.fxml"));
                        Scene sceneAjoutSujet = new Scene(pageParent);
                        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        app_Stage.hide(); 
                        app_Stage.setScene(sceneAjoutSujet);
                        app_Stage.show();
                    } catch (IOException ex) {
                        Logger.getLogger(SujetShowFController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
    }

    @FXML
    private void like2(MouseEvent event) {
       // System.out.println("ok");
    }
    
    
}
