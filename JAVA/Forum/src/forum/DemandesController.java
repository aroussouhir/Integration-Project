/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceSujet;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import pdfTest.mailTest;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class DemandesController implements Initializable {

    @FXML
    private TableColumn<Sujet, Categorie> categorie;
    
    @FXML
    private TableColumn<Sujet, String> sujet;
    @FXML
    private TableColumn<Sujet, String> question;

    @FXML
    private TableColumn<Sujet, String> nomUser;
    
    ServiceSujet ss = new ServiceSujet();
    @FXML
    private TableView<Sujet> table;
    @FXML
    private ImageView imV;
    Image img = new Image("LOGO.png");
    @FXML
    private Pagination pagination;
    
    private final static int dataSize = 100;
    private final static int rowsPerPage = 4;
    @FXML
    private Button btnR;
    
    String nomU;
    String email;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnR.setStyle("-fx-background-color: #ccccff; ");
        imV.setImage(img);
        addButtonToTable();
        reinit();
    }   
    
    ObservableList<Sujet> sujets;
    
private void reinit()
    {
            try {
            sujets = ss.demandes2();
            
           categorie.setCellValueFactory(new PropertyValueFactory<Sujet, Categorie>("idC"));
           sujet.setCellValueFactory(new PropertyValueFactory<Sujet, String>("topic"));
           question.setCellValueFactory(new PropertyValueFactory<Sujet, String>("question"));
           nomUser.setCellValueFactory(new PropertyValueFactory<Sujet, String>("nomUser"));
           
          //  table.setItems(sujets);
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
    
     private void addButtonToTable(){
        TableColumn<Sujet, Void> colBtnShow = new TableColumn("Action");
        TableColumn<Sujet, Void> colBtn = new TableColumn("Action");
        TableColumn<Sujet, Void> colBtnS = new TableColumn("Action");
        colBtnShow.setMinWidth(40);
        colBtn.setMinWidth(40);
        colBtnS.setMinWidth(40);
        
            Callback<TableColumn<Sujet, Void>, TableCell<Sujet, Void>> cellFactoryShow = new Callback<TableColumn<Sujet, Void>, TableCell<Sujet, Void>>() {
            @Override    
            public TableCell<Sujet, Void> call(final TableColumn<Sujet, Void> param) {
                final TableCell<Sujet, Void> cell = new TableCell<Sujet, Void>() {

                    private final Button btn = new Button("Voir");
                    

                    {
                        btn.setStyle("-fx-background-color: #b3c6ff; ");
                         btn.setOnAction((ActionEvent event) -> {
                            Sujet data1 = getTableView().getItems().get(getIndex());
                            
                            //redirection
                             try {
                                 
                                 FXMLLoader loader= new FXMLLoader(getClass().getResource("DemandeShow.fxml"));
                                 Parent root1 = (Parent) loader.load();
                                 
                                 DemandeShowController show = loader.getController();
                                 
                                 show.export(data1);
       
                                 Scene sceneAjoutSujet = new Scene(root1);
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
        
        
        Callback<TableColumn<Sujet, Void>, TableCell<Sujet, Void>> cellFactory = new Callback<TableColumn<Sujet, Void>, TableCell<Sujet, Void>>() {
            @Override    
            public TableCell<Sujet, Void> call(final TableColumn<Sujet, Void> param) {
                final TableCell<Sujet, Void> cell = new TableCell<Sujet, Void>() {

                    private final Button btn = new Button("Valider");
                    

                    {
                        btn.setStyle("-fx-background-color: #A0F7A9; ");
                         btn.setOnAction((ActionEvent event) -> {
                             
                            Sujet data = getTableView().getItems().get(getIndex());
                            nomU = data.getNomUser();
                            email = data.getEmailUser();
                            mailTest mail =new mailTest();
                            mail.envoyer("Bonjour "+data.getNomUser()+", votre publication a été validée : \n"+ data.toString2(), data.getEmailUser());
                             try {
                                 ss.valider(data);
                                 
                                 reinit();                                
                                 } catch (SQLException ex) {
                                 Logger.getLogger(DemandesController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        Callback<TableColumn<Sujet, Void>, TableCell<Sujet, Void>> cellFactorySupp = new Callback<TableColumn<Sujet, Void>, TableCell<Sujet, Void>>() {
           
            public TableCell<Sujet, Void> call(final TableColumn<Sujet, Void> param) {
                final TableCell<Sujet, Void> cell = new TableCell<Sujet, Void>() {

                    private final Button btnS = new Button("Rejeter");
                    

                    {
                       btnS.setStyle("-fx-background-color: #ffccde; ");
                       btnS.setOnAction((ActionEvent event) -> {
                       Sujet data = getTableView().getItems().get(getIndex());
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
                               ss.delete(data);
                               reinit();
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
        
        colBtnShow.setCellFactory(cellFactoryShow);
        colBtn.setCellFactory(cellFactory);
        colBtnS.setCellFactory(cellFactorySupp);
       
        table.getColumns().addAll(colBtnShow,colBtn,colBtnS);
    }

    @FXML
    private void retour(ActionEvent event)  throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("Sujets.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }
    
}

