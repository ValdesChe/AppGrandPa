package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AgendaController {


    @FXML
    private Label quitter;

    @FXML
    private Button btnProfil, btnContacts, btnAgenda, btnMessagerie, btnHDP, btnPharmacie;


    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }






}
