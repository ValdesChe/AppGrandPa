package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/sample.fxml"));
        primaryStage.setTitle("REMIND ME");
        primaryStage.setScene(new Scene(root, 500, 275));
        /*Platform.runLater(new Runnable() {
            @Override
            public void run() {
                root = FXMLLoader.load(getClass().getResource("../views/contact.fxml"));
            }
        });*/
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
