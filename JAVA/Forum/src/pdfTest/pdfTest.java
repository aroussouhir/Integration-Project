/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author arous souhir
 */
public class pdfTest extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        init(primaryStage);
    }
    
    private void init(Stage primaryStage){
        HBox root = new HBox();
        Scene scene = new Scene(root, 450, 330);
        
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Categorie");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Questions");
        
        LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("Traitement des donnees");
        
        XYChart.Series<String, Number> data = new XYChart.Series<>();
        data.setName("Preferences");
        data.getData().add(new XYChart.Data<String, Number>("Mecanique VTT", 670));
        data.getData().add(new XYChart.Data<String, Number>("Velo Route", 382));
        data.getData().add(new XYChart.Data<String, Number>("Autres", 580));
        data.getData().add(new XYChart.Data<String, Number>("Ventes", 210));
        data.getData().add(new XYChart.Data<String, Number>("Accessoires", 459));
        data.getData().add(new XYChart.Data<String, Number>("Services", 73));
        
        lineChart.getData().add(data);
        root.getChildren().add(lineChart);
        
        primaryStage.setTitle("Chart Forum");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }


    
}
