
//Packages and Imports

package controllers.addevent;


import classes.database.DatabaseConnector;
import classes.utils.Model;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import controllers.AgendaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddEventController implements Initializable {
    
    // Controllers
     private AgendaController mainController ;
     
     
    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------
    DatabaseConnector databaseHandler;
    //--------------------------------------------------------------------
    
    
    //Set main controller
    public void setMainController(AgendaController mainController) {
        this.mainController = mainController ;
    }

    // Structure
    @FXML
    private Label topLabel;


    @FXML
    private AnchorPane rootPane;
    
    // Text fields
    @FXML
    private JFXTextField subject;
    
    @FXML
    private JFXComboBox<String> heuresSelect;

    @FXML
    private JFXComboBox<String> minutesSelect;

    @FXML
    private TextArea descriptionEvent;

    // Buttons
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    
    // Date picker
    @FXML
    private JFXDatePicker date;
    
    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    
    @FXML
    void exit(MouseEvent event) {
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void cancel(MouseEvent event) {
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    //Function that inserts a new event in the database
     @FXML
    void save(MouseEvent event) {

        
        // Define date format
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");        
        
        //Check if the user inputted information in all required fields!
        if(subject.getText().isEmpty() || heuresSelect.getSelectionModel().isEmpty()
                || minutesSelect.getSelectionModel().isEmpty()
                ||date.getValue() == null){
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Veuillez remplir les champs du formaulaire !");
            alertMessage.showAndWait();
            return;
        }
        
        //Check if the event descritption contains the character ~ because it cannot contain it due to database and filtering issues
        if (subject.getText().contains("~") || descriptionEvent.getText().contains("~"))
        {
            //Show message indicating that the event description cannot contain the character ~
            Alert alertMessage = new Alert(Alert.AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Caractère spécial  ( ~ )  nom autorisé !");
            alertMessage.showAndWait();
            return;
        }
        
        //If all data is inputted correctly and validated, then add the event:
        
        // Get the date value from the date picker
        String calendarDate = date.getValue().format(myFormat);
        
        // Subject for the event
        String eventSubject = subject.getText();

        // Event's hour and minutes
         String evtHour = heuresSelect.getValue();
         String evtMin = minutesSelect.getValue();

        // Desciption/ Notes for the  event
        String eventDescript = descriptionEvent.getText();



        //---------------------------------------------------------
        //Insert new event into the 'evenement' table in the database
        
        //Query to get ID for the selected Term
        String insertQuery = "INSERT INTO evenement (title , description, dateEvent , heureEvent ) values("
                + "'" + eventSubject + "', "
                + "'" + eventDescript + "', "
                + "'" + calendarDate + "', "
                + "'" + evtHour + "-" + evtMin+ "'"
                + ")";
        
        
        //Check if insertion into database was successful, and show message either if it was or not
        if(databaseHandler.executeAction(insertQuery)) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Evènement correctement inséré !");
            alertMessage.showAndWait();
        }
        else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Erreur d'enregistrement !\n Veuillez reessayer avec de nouvelles informations !");
            alertMessage.showAndWait();
        }

        //Show the new event on the calendar according to the selected filters
        mainController.repaintView();
            
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }








    //Function that fills the date picker based on the clicked date 
    void autofillDatePicker() {

        // Get selected day, month, and year and autofill date selection
       int day = Model.getInstance().event_day;
       int month = Model.getInstance().event_month + 1;
       int year = Model.getInstance().event_year;
       
       // Set default value for datepicker
       date.setValue(LocalDate.of(year, month, day));

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //*** Instantiate DBHandler object *******************
        databaseHandler = new DatabaseConnector();
        //****************************************************

        //Fill the date picker
        autofillDatePicker();

        //Get the list of exisitng terms from the database and show them in the correspondent drop-down menu


        List<String> listHours = Arrays.asList(databaseHandler.getHoursEvent());
        ObservableList<String> hours = FXCollections.observableList(listHours);
        //Show list of terms in the drop-down menu
        heuresSelect.setItems(hours);

        List<String> listMinutes = Arrays.asList(databaseHandler.getMinutesEvent());
        ObservableList<String> min = FXCollections.observableList(listMinutes);
        //Show list of terms in the drop-down menu
        minutesSelect.setItems(min);

   
        //**********************************************************************
        // ************* Everything below is for Draggable Window ********
        
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        // Set up Mouse Dragging for the Event pop up window
        topLabel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
        // Change cursor when hover over draggable area
        topLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.HAND); //Change cursor to hand
            }
        });
        
        // Change cursor when hover over draggable area
        topLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                Scene scene = stage.getScene();
                scene.setCursor(Cursor.DEFAULT); //Change cursor to hand
            }
        });
    }    
    
}
