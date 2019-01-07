package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/mainForm.fxml"));
        primaryStage.setTitle("REMIND ME");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.initStyle(StageStyle.UNDECORATED);




        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset= event.getSceneX();
                yOffset= event.getSceneY();
            }
        });


        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX()-xOffset);
                primaryStage.setY(event.getScreenY()-yOffset);
            }
        });

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
