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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author arous souhir
 */
public class VoirImgController implements Initializable {

    @FXML
    private ImageView imV;
    @FXML
    private ImageView imvG;
    @FXML
    private ImageView imvD;
    
    Image D = new Image("fleche.png");
    Image G = new Image("flecheG.png");
    
    Image im1 ;
    Image im2 ;
    Image im3 ;
    
    int n;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
        public void export (Image m1, Image m2, Image m3, int nbr){
            
            n = nbr;
            
            if(nbr == 1)
            {
                imV.setImage(m1);  
                im1 = m1 ;
            }
            else if (nbr == 2 )
            {
                imV.setImage(m1);  
                imvD.setImage(D);
                im1 = m1;
                im2 = m2;
            }
            else if (nbr == 3)
            {
                imV.setImage(m1);  
                imvD.setImage(D);
                im1 = m1;
                im2 = m2;
                im3 = m3;
            }           
    }

    @FXML
    private void gauche(MouseEvent event) {
        
        if (imV.getImage()== im2)
        {
            imV.setImage(im1);
            imvG.setVisible(false);
            imvD.setImage(D);
            imvD.setVisible(true);
        }
        else if (imV.getImage()== im3)
        {
            imV.setImage(im2);
            imvG.setImage(G);
            imvG.setVisible(true);
            imvD.setVisible(true);
        }
    }

    @FXML
    private void droite(MouseEvent event) {

        if (imV.getImage()== im1 && n==2)
        {
            imV.setImage(im2);
            imvD.setVisible(false);
            imvG.setImage(G);
            imvG.setVisible(true);
        }
        else if (imV.getImage()== im1 && n==3)
        {
            imV.setImage(im2);
            imvG.setImage(G);  
            imvG.setVisible(true);
            imvD.setVisible(true);
        }
        else if (imV.getImage()== im2 && n==2)
        {
            imV.setImage(im3);
            imvD.setVisible(false);
            imvG.setImage(G);
            imvG.setVisible(true);
        }
        else if (imV.getImage()== im2 && n==3)
        {
            imV.setImage(im3);
            imvG.setImage(G);
            imvD.setVisible(false);
            imvG.setVisible(true);
        }
    }
    
}
