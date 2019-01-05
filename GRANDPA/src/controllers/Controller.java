package controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class Controller{


    @FXML
    private Label quitter;

    @FXML
    private Button btnProfil, btnContacts, btnAgenda, btnMessagerie, btnHDP, btnPharmacie;


    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }



    public void showPages(ActionEvent actionEvent) throws IOException {


        if (actionEvent.getSource().equals(btnProfil))
        {
            Parent monProfil= FXMLLoader.load(getClass().getResource("../views/monProfil.fxml"));
            Scene s = new Scene(monProfil, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(s);
            window.show();
        }

        if (actionEvent.getSource().equals(btnContacts))
        {
            Parent contacts= FXMLLoader.load(getClass().getResource("../views/contacts.fxml"));
            Scene s = new Scene(contacts, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(s);
            window.show();
        }

        if (actionEvent.getSource().equals(btnAgenda))
        {
            Parent agenda= FXMLLoader.load(getClass().getResource("../views/agenda.fxml"));
            Scene s = new Scene(agenda, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(s);
            window.show();
        }

        if (actionEvent.getSource().equals(btnMessagerie))
        {
            Parent messagerie= FXMLLoader.load(getClass().getResource("../views/messagerie.fxml"));
            Scene s = new Scene(messagerie, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(s);
            window.show();
        }

        if (actionEvent.getSource().equals(btnHDP))
        {
            Parent heurePriere= FXMLLoader.load(getClass().getResource("../views/heurePriere.fxml"));
            Scene s = new Scene(heurePriere, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(s);
            window.show();
        }

        if (actionEvent.getSource().equals(btnPharmacie))
        {
            Parent pharmacie= FXMLLoader.load(getClass().getResource("../views/pharmacie.fxml"));
            Scene s = new Scene(pharmacie, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(s);
            window.show();
        }


    }




}


