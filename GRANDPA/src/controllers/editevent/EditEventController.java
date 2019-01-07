
//Packages and Imports

package controllers.editevent;

import classes.utils.Model;
import classes.database.DatabaseConnector;
import controllers.addevent.AddEventController;
import controllers.AgendaController;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
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
    private JFXTextField subject;
    @FXML
    private JFXComboBox<String> termSelect;
    @FXML
    private JFXDatePicker date;
    @FXML
    private AnchorPane rootPane;
    
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
        int termID = Model.getInstance().event_term_id;
        String descript = Model.getInstance().event_subject;
       
        //Query to get ID for the selected Term
        String getIDQuery = "SELECT TermName From TERMS "
                + "WHERE TERMS.TermID= " + termID + " ";
        
        //Varialbe that holds the name of the current event's term based on a given term ID
        String chosenTermName = "";

        //Store the results from executing the Query
        ResultSet result = databaseHandler.executeQuery(getIDQuery);
        //Try-catch statements that will get the ID if a result was actually gotten back from the database
        try {
             while(result.next()){
                 //store ID into the corresponding variable
                 chosenTermName = result.getString("TermName");
             }
        } catch (SQLException ex) {
             Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       // Set default value for datepicker
       date.setValue(LocalDate.of(year, month, day));
       
       // Fill description field
       subject.setText(descript);
       
       //Fill term drop-down menu with current event's term
       termSelect.getSelectionModel().select(chosenTermName);
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
        int eventID = Model.getInstance().event_term_id;
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
        String term = termSelect.getValue();
        
        
        //Check if the event descritption contains the character ~ because it cannot contain it due to database and filtering issues
        if (newEventSubject.contains("~"))
        {
            //Show message indicating that the event description cannot contain the character ~
            Alert alertMessage = new Alert(AlertType.WARNING);
            alertMessage.setHeaderText(null);
            alertMessage.setContentText("Event Description cannot contain the character ~");
            alertMessage.showAndWait();
            return;
        }


        // Close the window
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();

    }


    public void deleteEvent() {

        //**********************************************************************
        //*******   Get INFO of the Event to be edited/upated             ******
        //*******   which is the term ID, event description, event date,  ******
        //*******   and calendar name of the event to be edited           ******
        //**********************************************************************
        int day = Model.getInstance().event_day;
        int month = Model.getInstance().event_month + 1;
        int year = Model.getInstance().event_year;
        String eventDate = year + "-" + month + "-" + day;
        int termID = Model.getInstance().event_term_id;
        String descript = Model.getInstance().event_subject;
        String calName = Model.getInstance().calendar_name;

        //Get the original date of the event to be updated in the format yyyy-mm-dd
        SimpleDateFormat auxDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String auxDate = "empty";

        
    }
    
}// end of EditEventController class
