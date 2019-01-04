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
    private Button btnProfil;


    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }



    public void showProfilPage(ActionEvent actionEvent) throws IOException {
        Parent monProfil= FXMLLoader.load(getClass().getResource("../views/monProfil.fxml"));
        Scene s = new Scene(monProfil, 700, 500);


        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(s);
        window.show();
    }
}


