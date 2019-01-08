package classes.prayer;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ThreadAdane extends Thread {
    private int sleepTime=0;
    private String titre="C'est l'heure";
    private  String texte="Il est l'heure de la prière!";
    private String sound ="azan1.mp3";


    public ThreadAdane(String sound, int sleepTime, String titre, String texte) {
        this.sound = sound;
        this.sleepTime = sleepTime;
        this.titre = titre;
        this.texte = texte;
    }

    public  ThreadAdane(int sleepTime){
        this.sleepTime = sleepTime;
    }

    @Override
    public void run(){

        try {
            Thread.sleep(1000*sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Notifications notification = Notifications.create()
                .title(titre)
                .text(texte)
                .graphic(null)
                .hideAfter(Duration.seconds(60))
                .position(Pos.TOP_RIGHT);


        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                System.out.println("file:///" + getClass()
                        .getResource("./../../ressources/sound/"+sound)
                        .getFile().replaceFirst("/","")
                        .replace(":/" , "://"));

                Media musicfile = new Media("file:///" + getClass()
                        .getResource("./../../ressources/sound/"+sound)
                        .getFile().replaceFirst("/","")
                        .replace(":/" , "://"));


               MediaPlayer med = new MediaPlayer(musicfile);
                med.setAutoPlay(true);


                notification.showConfirm();


            }
        });



    }


}
