package controllers;

import classes.prayer.PrayerFetcher;
import classes.utils.Prayers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class HeurePriereControler extends Controller implements Initializable {

    @FXML
    Label fajrLbl, dohrLbl, asrLbl, mghrebLbl, ishaeLbl, villeLbl, heureLbl;

    PrayerFetcher prayerFetcher = new PrayerFetcher("roma");

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        clock();
        try {
            prayerFetcher.fetchPrayers();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Prayers prayers = prayerFetcher.getPrayers();



        villeLbl.setText(prayers.getCity());
        fajrLbl.setText(prayers.getItems().get(0).getFajr());
        dohrLbl.setText(prayers.getItems().get(0).getDhuhr());
        asrLbl.setText(prayers.getItems().get(0).getAsr());
        mghrebLbl.setText(prayers.getItems().get(0).getMaghrib());
        ishaeLbl.setText(prayers.getItems().get(0).getIsha());






    }


    public void clock(){

        Thread clock = new Thread(() -> {
            try {

                while (true) {

                    Calendar calendar = new GregorianCalendar();
                    int jour = calendar.get(Calendar.DAY_OF_MONTH);
                    int mois = calendar.get(Calendar.MONTH);
                    int annee = calendar.get(Calendar.YEAR);


                    int heure = calendar.get(Calendar.HOUR);
                    int minute = calendar.get(Calendar.MINUTE);
                    int seconde = calendar.get(Calendar.SECOND);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            heureLbl.setText("Date : " + jour + "/" + mois+1 + "/" + annee + "   " + heure + ":" + minute + ":" + seconde);
                        }
                    });

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        clock.start();

    }


}
