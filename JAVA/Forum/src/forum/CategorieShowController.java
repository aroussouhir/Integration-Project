/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Commentaire;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCategorie;
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
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class CategorieShowController implements Initializable {

    @FXML
    private Label nomCateg;
    @FXML
    private TableColumn<Sujet, String> topic;
    @FXML
    private TableColumn<Sujet, String> question;
    @FXML
    private TableColumn<Sujet, Date> createdAt;
    @FXML
    private TableColumn<Sujet, String> nomuser;
    @FXML
    private TableColumn<Sujet, Integer> nbJ;
    
    Categorie c2;
    
    ServiceSujet sc = new ServiceSujet();
    
    ServiceCategorie scateg = new ServiceCategorie();
    
    @FXML
    private TableView<Sujet> table;
    TableColumn<Sujet, Void> colBtnShow = new TableColumn("Action");
    TableColumn<Sujet, Void> colBtn = new TableColumn("Action");
    TableColumn<Sujet, Void> colBtnS = new TableColumn("Action");
    @FXML
    private ImageView imV;
    Image img = new Image("LOGO.png");
    @FXML
    private Pagination pagination;
    
    private final static int dataSize = 100;
    private final static int rowsPerPage = 4;
    @FXML
    private Button btnCateg;
    @FXML
    private Button btnSujets;
    @FXML
    private ImageView imVAjout;
    
    Image imgAjout = new Image("add.png");
    @FXML
    private ImageView imvH;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnCateg.setStyle("-fx-background-color: #ccccff; ");
        btnSujets.setStyle("-fx-background-color: #ccccff; ");
        imV.setImage(img);
        imVAjout.setImage(imgAjout);
        imvH.setImage(new Image("heart.png"));
        Tooltip.install(imVAjout, new Tooltip("Ajouter un sujet"));
        table.getColumns().addAll(colBtnShow,colBtn,colBtnS);
    }   
    
    ObservableList<Sujet> sujets;
    
        public void export (Categorie c) throws SQLException{
        
        c2 = c ;
        nomCateg.setText(c.getNom());
        
        sujets = scateg.readSujets2(c.getId());
        topic.setCellValueFactory(new PropertyValueFactory<Sujet, String>("topic"));
        question.setCellValueFactory(new PropertyValueFactory<Sujet, String>("question"));
        createdAt.setCellValueFactory(new PropertyValueFactory<Sujet, Date>("createdAt"));
        nomuser.setCellValueFactory(new PropertyValueFactory<Sujet, String>("nomUser"));
        nbJ.setCellValueFactory(new PropertyValueFactory<Sujet, Integer>("nbJ"));

        //table.setItems(sujets);
        pagination.setPageFactory(this::createPage);
        
        addButtonToTable(c);
    }
        
    private Node createPage(int pageIndex)
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, sujets.size());
        table.setItems(FXCollections.observableArrayList(sujets.subList(fromIndex, toIndex)));
        return table;
    }
        
        private void addButtonToTable(Categorie s){
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
                                 
                                 FXMLLoader loader= new FXMLLoader(getClass().getResource("SujetShow.fxml"));
                                 Parent root1 = (Parent) loader.load();
                                 
                                 SujetShowController show = loader.getController();
                                 
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

                    private final Button btn = new Button("Modifier");
                    

                    {
                        btn.setStyle("-fx-background-color: #b3c6ff; ");
                         btn.setOnAction((ActionEvent event) -> {
                            Sujet data = getTableView().getItems().get(getIndex());
                            System.out.println(data.getId());
                            
                            //redirection
                             try {
                                 
                                 FXMLLoader loader= new FXMLLoader(getClass().getResource("ModificationSujet.fxml"));
                                 Parent root = (Parent) loader.load();
                                 
                                 ModificationSujetController sec = loader.getController();
                                 
                                 sec.export(data);
       
                                 Scene sceneAjoutSujet = new Scene(root);
                                 sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
                                 Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                 app_Stage.hide(); 
                                 app_Stage.setScene(sceneAjoutSujet);
                                 app_Stage.show();

                             } catch (IOException ex) {
                                 Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
                             } catch (SQLException ex) {
                                Logger.getLogger(CategorieShowController.class.getName()).log(Level.SEVERE, null, ex);
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

                    private final Button btnS = new Button("Supprimer");
                    

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
        colBtnShow.setCellFactory(cellFactoryShow);
        colBtn.setCellFactory(cellFactory);
        colBtnS.setCellFactory(cellFactorySupp);    
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
    private void ajoutS(MouseEvent event) throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("AjoutSujet.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }
    
}
