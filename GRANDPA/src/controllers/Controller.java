package controllers;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class Controller{
    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    protected Label quitter;

    @FXML
    protected Button btnProfil, btnContacts, btnAgenda, btnMessagerie, btnHDP, btnPharmacie;


    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }



    public void showPages(ActionEvent actionEvent) throws IOException {


        if (actionEvent.getSource().equals(btnProfil))
        {
            Parent monProfile= FXMLLoader.load(getClass().getResource("../views/monProfile.fxml"));
            Scene s = new Scene(monProfile, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            monProfile.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset= event.getSceneX();
                    yOffset= event.getSceneY();
                }
            });

            monProfile.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.setX(event.getScreenX()-xOffset);
                    window.setY(event.getScreenY()-yOffset);
                }
            });

            window.setScene(s);
            window.show();
        }

        else if (actionEvent.getSource().equals(btnContacts))
        {
            Parent contacts= FXMLLoader.load(getClass().getResource("../views/contacts.fxml"));
            Scene s = new Scene(contacts, 700, 500);

            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            contacts.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset= event.getSceneX();
                    yOffset= event.getSceneY();
                }
            });

            contacts.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.setX(event.getScreenX()-xOffset);
                    window.setY(event.getScreenY()-yOffset);
                }
            });

            window.setScene(s);
            window.show();
        }

        else if (actionEvent.getSource().equals(btnAgenda))
        {
            Parent agenda= FXMLLoader.load(getClass().getResource("../views/agenda.fxml"));
            Scene s = new Scene(agenda, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            agenda.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset= event.getSceneX();
                    yOffset= event.getSceneY();
                }
            });

            agenda.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.setX(event.getScreenX()-xOffset);
                    window.setY(event.getScreenY()-yOffset);
                }
            });

            window.setScene(s);
            window.show();
        }

        else if (actionEvent.getSource().equals(btnMessagerie))
        {
            Parent messagerie= FXMLLoader.load(getClass().getResource("../views/messagerie.fxml"));
            Scene s = new Scene(messagerie, 700, 500);

            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            messagerie.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset= event.getSceneX();
                    yOffset= event.getSceneY();
                }
            });

            messagerie.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.setX(event.getScreenX()-xOffset);
                    window.setY(event.getScreenY()-yOffset);
                }
            });

            window.setScene(s);
            window.show();
        }

        else if (actionEvent.getSource().equals(btnHDP))
        {
            Parent heurePriere= FXMLLoader.load(getClass().getResource("../views/heurePriere.fxml"));
            Scene s = new Scene(heurePriere, 700, 500);


            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            heurePriere.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset= event.getSceneX();
                    yOffset= event.getSceneY();
                }
            });

            heurePriere.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.setX(event.getScreenX()-xOffset);
                    window.setY(event.getScreenY()-yOffset);
                }
            });


            window.setScene(s);
            window.show();
        }

        else if (actionEvent.getSource().equals(btnPharmacie))
        {
            Parent pharmacie= FXMLLoader.load(getClass().getResource("../views/pharmacie.fxml"));
            Scene s = new Scene(pharmacie, 700, 500);

            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            pharmacie.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset= event.getSceneX();
                    yOffset= event.getSceneY();
                }
            });

            pharmacie.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.setX(event.getScreenX()-xOffset);
                    window.setY(event.getScreenY()-yOffset);
                }
            });


            window.setScene(s);
            window.show();
        }



    }







}


