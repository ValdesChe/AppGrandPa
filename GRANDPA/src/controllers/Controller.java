package controllers;

<<<<<<< HEAD
public class Controller {
    public int vt;
=======
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(MouseEvent event){
        System.exit(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

>>>>>>> df6fe97ecbd9ae10cd1109a055508902a323fee5

}
