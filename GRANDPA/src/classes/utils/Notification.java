package classes.utils;

import classes.database.DatabaseConnector;
import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

public class Notification extends Application{
    MediaPlayer mediaplayer;



    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage){


       // Media musicfile = new Media("file:///P:/JAVA/JAVAFX/AppGrandPa/GRANDPA/src/ressources/sound/azan1.mp3");
        /* System.out.println("file:///" + getClass()
                .getResource("./../../ressources/sound/azan1.mp3")
                .getFile().replaceFirst("/","")
                .replace(":/" , "://"));

        */
         Media musicfile = new Media("file:///" + getClass()
                 .getResource("./../../ressources/sound/azan1.mp3")
                 .getFile().replaceFirst("/","")
                 .replace(":/" , "://"));


        mediaplayer = new MediaPlayer(musicfile);
        mediaplayer.setAutoPlay(true);
       


    }
}