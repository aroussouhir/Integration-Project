/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forum;

import com.esprit.Entite.Categorie;
import com.esprit.Entite.Sujet;
import com.esprit.Service.ServiceCategorie;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class CategoriesController implements Initializable {

    @FXML
    private TableView<Categorie> table;
    @FXML
    private TableColumn<Categorie, String> nom;
    
    ServiceCategorie categorie = new ServiceCategorie();
    @FXML
    private TextField nomCateg;
    @FXML
    private Label confirmation;
    @FXML
    private ImageView imV;
       
    Image img = new Image("LOGO.png");
    @FXML
    private Pagination pagination;
    
    private final static int dataSize = 100;
    private final static int rowsPerPage = 10;
    @FXML
    private Button btnAjout;
    @FXML
    private Button btnSujets;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        btnSujets.setStyle("-fx-background-color: #ccccff; ");
        btnAjout.setStyle("-fx-background-color: #FFC8A8  ; ");
        imV.setImage(img);
        addButtonToTable();
        reinit();
    }  
    
    ObservableList<Categorie> ListCateg;
    
    private void reinit()
    {
        try {
            ListCateg = categorie.readAll2();
            nom.setCellValueFactory(new PropertyValueFactory<Categorie, String>("nom"));
            //table.setItems(ListCateg);
            pagination.setPageFactory(this::createPage);
            
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajoutCateg(ActionEvent event) throws SQLException {
        
        ServiceCategorie categorie = new ServiceCategorie();
        Categorie cat = new Categorie();
        
        cat.setNom(nomCateg.getText());
        
        if(categorie.exist(cat))
        {
            confirmation.setText("Existe deja");
        }
        else{
        categorie.ajouter(cat);   
        reinit();
        }
    }
    
    private Node createPage(int pageIndex)
    {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, ListCateg.size());
        table.setItems(FXCollections.observableArrayList(ListCateg.subList(fromIndex, toIndex)));
        return table;
    }
    
    private void addButtonToTable(){
        TableColumn<Categorie, Void> colBtnShow = new TableColumn("Action");
        TableColumn<Categorie, Void> colBtnS = new TableColumn("Action");
        colBtnShow.setMinWidth(40);
        colBtnS.setMinWidth(40);
        
        Callback<TableColumn<Categorie, Void>, TableCell<Categorie, Void>> cellFactoryShow = new Callback<TableColumn<Categorie, Void>, TableCell<Categorie, Void>>() {
            @Override    
            public TableCell<Categorie, Void> call(final TableColumn<Categorie, Void> param) {
                final TableCell<Categorie, Void> cell = new TableCell<Categorie, Void>() {

                    private final Button btn = new Button("Voir");
                    

                    {
                        btn.setStyle("-fx-background-color: #b3c6ff; ");
                         btn.setOnAction((ActionEvent event) -> {
                            Categorie data1 = getTableView().getItems().get(getIndex());
                            
                            //redirection
                             try {
                                 
                                 FXMLLoader loader= new FXMLLoader(getClass().getResource("CategorieShow.fxml"));
                                 Parent root1 = (Parent) loader.load();
                                 
                                 CategorieShowController show = loader.getController();
                                 
                                 show.export(data1);
       
                                 Scene sceneAjoutSujet = new Scene(root1);
                                 sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
                                 Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                 app_Stage.hide(); 
                                 app_Stage.setScene(sceneAjoutSujet);
                                 app_Stage.show();

                             } catch (IOException ex) {
                                 Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
                             } catch (SQLException ex) {
                                 Logger.getLogger(CategoriesController.class.getName()).log(Level.SEVERE, null, ex);
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

        Callback<TableColumn<Categorie, Void>, TableCell<Categorie, Void>> cellFactorySupp = new Callback<TableColumn<Categorie, Void>, TableCell<Categorie, Void>>() {
           
            public TableCell<Categorie, Void> call(final TableColumn<Categorie, Void> param) {
                final TableCell<Categorie, Void> cell = new TableCell<Categorie, Void>() {

                    private final Button btnS = new Button("Supprimer");
                    

                    {
                        btnS.setStyle("-fx-background-color: #ffccde; ");
                       btnS.setOnAction((ActionEvent event) -> {
                       Categorie data = getTableView().getItems().get(getIndex());
                       
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
                                        categorie.delete(data);
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
        colBtnS.setCellFactory(cellFactorySupp);
       
        table.getColumns().addAll(colBtnShow,colBtnS);
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Parent pageParent = FXMLLoader.load(getClass().getResource("Sujets.fxml"));
        Scene sceneAjoutSujet = new Scene(pageParent);
        sceneAjoutSujet.getStylesheets().add("/CSS/myCSS.css");
        Stage app_Stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_Stage.hide(); 
        app_Stage.setScene(sceneAjoutSujet);
        app_Stage.show();
    }
    
    

    
}
