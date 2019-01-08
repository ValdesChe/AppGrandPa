
//Packages and Imports

package controllers.editevent;

import classes.utils.Model;
import classes.database.DatabaseConnector;
import com.jfoenix.controls.JFXButton;
import com.sun.org.apache.xpath.internal.operations.Mod;
import controllers.addevent.AddEventController;
import controllers.AgendaController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditEventController implements Initializable {
    
    // Main Controller -------------------------------
    private AgendaController mainController;
    // -------------------------------------------------------------------
    
    //--------------------------------------------------------------------
    //---------Database Object -------------------------------------------
    DatabaseConnector databaseHandler;
    //--------------------------------------------------------------------
    @FXML
    private Label topLabel;

    @FXML
    private AnchorPane rootPane;

    // Date picker
    @FXML
    private JFXDatePicker date;

    // Text fields
    @FXML
    private JFXTextField subject;

    @FXML
    private JFXComboBox<String> heuresSelect;

    @FXML
    private JFXComboBox<String> minutesSelect;

    @FXML
    private TextArea descriptionEvent;

    
    //Set main controller
    public void setMainController(AgendaController mainController) {
        this.mainController = mainController ;
    }

    // These fields are for mouse dragging of window
    private double xOffset;
    private double yOffset;
    

    //Function that fills the date picker based on the clicked event's date
    void autofillDatePicker() {
        
        // Get selected day, month, and year and autofill date selection
        int day = Model.getInstance().event_day;
        int month = Model.getInstance().event_month + 1;
        int year = Model.getInstance().event_year;
        int eventID = Model.getInstance().event_id;

        String title = Model.getInstance().event_subject;
        String descript = Model.getInstance().event_description;


        String eventHours = Model.getInstance().event_hour;
        String eventMin = Model.getInstance().event_minutes;

       // Set default value for datepicker
       date.setValue(LocalDate.of(year, month, day));
       
       // Fill description field
       subject.setText(title);
       descriptionEvent.setText(descript);

       heuresSelect.getSelectionModel().select(eventHours);
       minutesSelect.getSelectionModel().select(eventMin);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //*** Instantiate DatabaseConnector object *******************
        databaseHandler = new DatabaseConnector();
        //****************************************************


        List<String> listHours = Arrays.asList(databaseHandler.getHoursEvent());
        ObservableList<String> hours = FXCollections.observableList(listHours);
        //Show list of terms in the drop-down menu
        heuresSelect.setItems(hours);

        List<String> listMinutes = Arrays.asList(databaseHandler.getMinutesEvent());
        ObservableList<String> min = FXCollections.observableList(listMinutes);
        //Show list of terms in the drop-down menu
        minutesSelect.setItems(min);

        //Fill the date picker
        autofillDatePicker();

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

    @FXML
    private void exit(MouseEvent event) {
        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void update(MouseEvent event) {
        updateEvent();
    }

    //Function that deletes a selected event
    @FXML
    private void delete(MouseEvent event) {
        
        //Show confirmation dialog to make sure the user want to delete the selected event
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Event Deletion");
        alert.setContentText("Are you sure you want to delete this event?");
        //Customize the buttons in the confirmation dialog
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        //Set buttons onto the confirmation window
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        
        //Get the user's answer on whether deleting or not
        Optional<ButtonType> result = alert.showAndWait();
        
        //If the user wants to delete the event, call the function that deletes the event. Otherwise, close the window
        if (result.get() == buttonTypeYes){
            deleteEvent();
        } 
        else 
        {
            // Close the window
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close(); 
        }
        
    }
    
    
    //Function that updates the information of a selected event from the calendar
    public void updateEvent(){
        
        // Define date format
        DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        //**********************************************************************
        //*******   Get OLD INFO of the Event to be edited/upated         ******
        //*******   which is the term ID, event description, event date,  ******
        //*******   and calendar name of the event to be edited           ******
        //**********************************************************************
        int day = Model.getInstance().event_day;
        int month = Model.getInstance().event_month + 1;
        int year = Model.getInstance().event_year;
        String eventDate = year + "-" + month + "-" + day;
        //int eventID = Model.getInstance().event_term_id;
        String descript = Model.getInstance().event_subject;

        
        //Get the original date of the event to be updated in the format yyyy-mm-dd
        SimpleDateFormat auxDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String auxDate = "empty";
        Date auxEventDate = new Date();
        try {
            auxEventDate = auxDateFormat.parse(eventDate);
            auxDate = auxDateFormat.format(auxEventDate);
        } catch (ParseException ex) {
            Logger.getLogger(EditEventController.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
        //**********************************************************************
        //******    Get NEW INFO for the Event to be edited/updated   **********
        //**********************************************************************
        
        // Get the date value from the date picker
        String newCalendarDate = date.getValue().format(myFormat);
        // Subject for the event
        String newEventSubject = subject.getText();
        // Get term that was selected by the user


        // Event's hour and minutes
        String newEventHour = heuresSelect.getValue();
        String newEventMin = minutesSelect.getValue();

        // Desciption/ Notes for the  event
        String newEventDescript = descriptionEvent.getText();


        //Check if the event descritption contains the character ~ because it cannot contain it due to database and filtering issues
        if (newEventSubject.contains("~") || newEventDescript.contains("~") )
        {
            //Show message indicating that the event description cannot contain the character ~
            Alert alertMessage = new Alert(AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Caractere irregulier rencontré ~");
            alertMessage.showAndWait();
            return;
        }

        // If all correct
        String updateEventQuery = "UPDATE evenement"
                + " SET "
                + "title='" + newEventSubject + "', "
                + "description='" + newEventDescript + "', "
                + "dateEvent='" + newCalendarDate + "' ,"
                + "heureEvent='" + newEventHour + "-" + newEventMin + "' "
                + " WHERE "
                + "id_event = " + Model.getInstance().event_id + " ";


        //Execute query in otder to update the info for the selected event
        //and
        //Check if the update of the event in the database was successful, and show message either if it was or not
        if(databaseHandler.executeAction(updateEventQuery)) {
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Votre évènement a été correctement mis à jour !");
            alertMessage.showAndWait();

            // Update view
            mainController.repaintView();

        }
        else //if there is an error
        {
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Echec lors de la mise à jour de l'évènement ! \n Probleme technique ...");
            alertMessage.showAndWait();
        }




        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }


    public void deleteEvent() {


        //Query that will delete the selected event
        String deleteEventQuery = "DELETE FROM evenement "
                + "WHERE "
                + "id_event = " + Model.getInstance().event_id + " ";

        //Execute query that deletes the selected event
        boolean eventWasDeleted = databaseHandler.executeAction(deleteEventQuery);

        if (eventWasDeleted)
        {
            //Show message indicating that the selected rule was deleted
            Alert alertMessage = new Alert(Alert.AlertType.INFORMATION);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("L'évènement ' "+ Model.getInstance().event_subject + " ' supprimé avec succès !");
            alertMessage.showAndWait();

            // Update view
            mainController.repaintView();

            // Close the window, so that when user clicks on "Manage Rules" only the remaining existing rules appear
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.close();
        }
        else
        {
            //Show message indicating that the rule could not be deleted
            Alert alertMessage = new Alert(Alert.AlertType.ERROR);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Echec de la suppression !");
            alertMessage.showAndWait();
        }
        
    }
    
}// end of EditEventController class
