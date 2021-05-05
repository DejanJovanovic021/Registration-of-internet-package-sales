
package main;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    
    public static void main(String[] args) {
        
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        URL fxmlUrl = getClass().getClassLoader().getResource("view/SaleView.fxml");
        GridPane root = FXMLLoader.<GridPane> load(fxmlUrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("view/style.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Registration of internet-package sale");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
}
