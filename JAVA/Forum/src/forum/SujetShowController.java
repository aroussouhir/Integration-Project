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
import com.esprit.Service.ServiceImg;
import com.esprit.Service.ServiceSujet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import pdfTest.mailTest;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class SujetShowController implements Initializable {

    @FXML
    private Label question;
    @FXML
    private Label topic;
    @FXML
    private TableView<Commentaire> table;
    @FXML
    private TableColumn<Commentaire, String> commentaire;
    
    ServiceCommentaire sc = new ServiceCommentaire();
    @FXML
    private TextArea NewCom;
    
    Sujet s2;
    
    TableColumn<Commentaire, Void> colBtn = new TableColumn("Action");
    TableColumn<Commentaire, Void> colBtnS = new TableColumn("Action");
    @FXML
    private ImageView imV;
    Image img = new Image("LOGO.png");
    @FXML
    private ImageView imS;
    @FXML
    private Pagination pagination;
    
    private final static int dataSize = 100;
    private final static int rowsPerPage = 4;
    @FXML
    private Button btnAjout;
    @FXML
    private Button btnSujets;
    @FXML
    private Button btnCateg;
    
    String nomU ="Souhir";
    String email ="souhir.arous@esprit.tn";
    @FXML
    private Label confirmation;
    @FXML
    private ImageView imvModif;
    @FXML
    private ImageView imvSupp;
    @FXML
    private ImageView imvEmail;
    
    Image imgModif =new Image("edit1.png");
    Image imgSupp =new Image("supp.png");
    Image imgEmail =new Image("email.png");
    
    int onglet = 0 ;
    Image img3;
    Image img2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        btnCateg.setStyle("-fx-background-color: #ccccff; ");
        btnSujets.setStyle("-fx-background-color: #ccccff; ");
        btnAjout.setStyle("-fx-background-color: #FFC8A8 ; ");
        imV.setImage(img);
        table.getColumns().addAll(colBtn,colBtnS);
        
        imvModif.setImage(imgModif);
        imvSupp.setImage(imgSupp);
        imvEmail.setImage(imgEmail);
        
        Tooltip.install(imvModif, new Tooltip("Modifier"));
        Tooltip.install(imvEmail, new Tooltip("Envoyer email"));
        Tooltip.install(imvSupp, new Tooltip("Supprimer"));
    }  
    
    ObservableList<Commentaire> commentaires;
    
    public void export (Sujet s) throws SQLException{
        
        s2 = s ;
        topic.setText(s.getTopic()+"\nAjoute le "+s.getCreatedAt()+" par "+s.getNomUser()+"\n"+s.getNbJ()+" J'aime");
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
        commentaire.setCellValueFactory(new PropertyValueFactory<Commentaire, String>("contenu"));
        pagination.setPageFactory(this::createPage);
        
        addButtonToTable(s);
    }
    
    private Node createPage(int pageIndex)
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, commentaires.size());
        table.setItems(FXCollections.observableArrayList(commentaires.subList(fromIndex, toIndex)));
        return table;
    }
    
     private void addButtonToTable(Sujet s){
        colBtn.setMinWidth(40);
        colBtnS.setMinWidth(40);
        
        Callback<TableColumn<Commentaire, Void>, TableCell<Commentaire, Void>> cellFactory = new Callback<TableColumn<Commentaire, Void>, TableCell<Commentaire, Void>>() {
            @Override    
            public TableCell<Commentaire, Void> call(final TableColumn<Commentaire, Void> param) {
                final TableCell<Commentaire, Void> cell = new TableCell<Commentaire, Void>() {

                    private final Button btn = new Button("Modifier");
                    

                    {
                        btn.setStyle("-fx-background-color: #b3c6ff; ");
                         btn.setOnAction((ActionEvent event) -> {
                            Commentaire data = getTableView().getItems().get(getIndex());
                            
                            //redirection
                             try {
                                 
                                 FXMLLoader loader= new FXMLLoader(getClass().getResource("ModificationCom.fxml"));
                                 Parent root = (Parent) loader.load();
                                 
                                 ModificationComController sec = loader.getController();
                                 
                                 sec.export(data);
       
                                 Scene sceneAjoutSujet = new Scene(root);
                                 sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
                                 Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                 app_Stage.hide(); 
                                 app_Stage.setScene(sceneAjoutSujet);
                                 app_Stage.show();

                             } catch (IOException ex) {
                                 Logger.getLogger(SujetsController.class.getName()).log(Level.SEVERE, null, ex);
                             }
                        });       
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        
        Callback<TableColumn<Commentaire, Void>, TableCell<Commentaire, Void>> cellFactorySupp = new Callback<TableColumn<Commentaire, Void>, TableCell<Commentaire, Void>>() {
           
            public TableCell<Commentaire, Void> call(final TableColumn<Commentaire, Void> param) {
                final TableCell<Commentaire, Void> cell = new TableCell<Commentaire, Void>() {

                    private final Button btnS = new Button("Supprimer");
                    

                    {
                       btnS.setStyle("-fx-background-color: #ffccde; ");
                       btnS.setOnAction((ActionEvent event) -> {
                       Commentaire data = getTableView().getItems().get(getIndex());
                         /*  try { 
                               sc.delete(data);
                               export(s);
                           } catch (SQLException ex) {
                               Logger.getLogger(SujetsController.class.getName()).log(Level.SEVERE, null, ex);
                           }*/
                         
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
                                        sc.delete(data);
                                        export(s);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(SujetsController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                               }
                    });
                    });
                                          
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnS);
                        }
                    }
                };
                return cell;
            }
        };
        
        colBtn.setCellFactory(cellFactory);
        colBtnS.setCellFactory(cellFactorySupp);    
    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {

        Commentaire c = new Commentaire(NewCom.getText(),s2, nomU, email);
        
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
    private void Bsujets(ActionEvent event)  throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("Sujets.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }

    @FXML
    private void Bcategories(ActionEvent event)  throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("Categories.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
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
    private void modifSujet(MouseEvent event) {
         try {
                                 
            FXMLLoader loader= new FXMLLoader(getClass().getResource("ModificationSujet.fxml"));
            Parent root = (Parent) loader.load();

            ModificationSujetController sec = loader.getController();

            sec.export(s2);

            Scene sceneAjoutSujet = new Scene(root);
            sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
            Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            app_Stage.hide(); 
            app_Stage.setScene(sceneAjoutSujet);
            app_Stage.show();

        } catch (IOException ex) {
            Logger.getLogger(SujetsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
           Logger.getLogger(SujetsController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    @FXML
    private void SuppSujet(MouseEvent event) {
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
                        Parent pageParent = FXMLLoader.load(getClass().getResource("Sujets.fxml"));
                        Scene sceneAjoutSujet = new Scene(pageParent);
                        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
                        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        app_Stage.hide(); 
                        app_Stage.setScene(sceneAjoutSujet);
                        app_Stage.show();
                    } catch (SQLException ex) {
                        Logger.getLogger(SujetsController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(SujetShowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
               }
    });
    }

    @FXML
    private void sendEmail(MouseEvent event) throws IOException {
        
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Email.fxml"));
        Parent root1 = (Parent) loader.load();

        EmailController show = loader.getController();

        show.export(s2);
        Scene scene2 = new Scene(root1);
        Stage anotherStage = new Stage();

        anotherStage.setScene(scene2);
        anotherStage.show();
    }
    
}
