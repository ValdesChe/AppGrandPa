package controllers;

import classes.mailer.JavaEmail;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MessagerieController extends Controller{

    @FXML
    private TextField emailField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea bodyField;

    @FXML
    private Button btnSubmitMail;

    public void btnSubmitMailer(){

        if (emailField.getText().equals("")){
            emailField.setStyle("-fx-border-color: red");
        }
        else{
            JavaEmail javaEmail = new JavaEmail();

            String [] table = new String[1];
            table[0] = emailField.getText();



            javaEmail.setMailServerProperties();
            try {
                javaEmail.createEmailMessage(table, subjectField.getText(), bodyField.getText());
                javaEmail.sendEmail();
            }
            catch (Exception e) {
                System.out.println("Erreur lors de l envoie du mail");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION );
            alert.setContentText("Email envoye avec success. ");
            alert.showAndWait();
            emailField.setText("");
            subjectField.setText("");
            bodyField.setText("");

        }

    }

}
