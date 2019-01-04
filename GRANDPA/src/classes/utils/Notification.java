package classes.utils;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Notification extends Application{
    MediaPlayer mediaplayer;



    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage){
        Media musicfile = new Media("file:///C:/Users/ISSAM/Desktop/AppGrandPa/GRANDPA/azan1.mp3");

        mediaplayer = new MediaPlayer(musicfile);
        mediaplayer.setAutoPlay(true);
       

        VBox root  = new VBox();
        root.getChildren().addAll();

        Scene scene = new Scene(root,200,200);
        stage.setScene(scene);

        stage.show();
    }
}