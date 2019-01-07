package controllers;

import classes.prayer.PrayerFetcher;
import classes.utils.Prayers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class HeurePriereControler extends Controller implements Initializable {

    @FXML
    Label fajrLbl, dohrLbl, asrLbl, mghrebLbl, ishaeLbl;

    PrayerFetcher prayerFetcher = new PrayerFetcher("roma");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            prayerFetcher.fetchPrayers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Prayers prayers = prayerFetcher.getPrayers();

        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        System.out.println(gregorianCalendar.getTimeZone());

        fajrLbl.setText(prayers.getItems().get(0).getFajr());
        dohrLbl.setText(prayers.getItems().get(0).getDhuhr());
        asrLbl.setText(prayers.getItems().get(0).getAsr());
        mghrebLbl.setText(prayers.getItems().get(0).getMaghrib());
        ishaeLbl.setText(prayers.getItems().get(0).getIsha());

        //System.out.println(prayers.getItems().get(0).getAsr());
    }



        /*System.out.println(
                prayerFetcher.getPrayers().getCountry()+
            " Lien Img: "+ prayerFetcher.getPrayers().getMap_image() + "\n" +
            " Date: "+ prayerFetcher.getPrayers().getItems().get(0).getDate_for() +  "\n" +
            " Isha Time: "+ prayerFetcher.getPrayers().getItems().get(0).getIsha()
        );
        */
}
