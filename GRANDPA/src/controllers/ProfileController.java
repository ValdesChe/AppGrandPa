package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Contact;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController extends Controller implements Initializable{

    Contact user = new Contact(1000, "YAZID", "Yassine", "+212 628 32 41 75", "mo.alitahir9@gmail.com","Tétouan");


    @FXML
    TextField nomTfd, prenomTfd, phoneTfd, emailTfd, villeTfd;


    public void updateProfile(ActionEvent actionEvent) {

        if (emailTfd.getText().equals("") || villeTfd.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "");
            alert.setHeaderText("Votre adresse email et votre localisation sont indispensables!");
            alert.setTitle("Information");
            alert.show();
        }
        else{
            user.setNom(nomTfd.getText());
            user.setPrenom(prenomTfd.getText());
            user.setTel(phoneTfd.getText());
            user.setEmail(emailTfd.getText());
            user.setVille(villeTfd.getText());


            Alert alert = new Alert(Alert.AlertType.INFORMATION, "");
            alert.setHeaderText("Vos informations sont modifiées avec succès!");
            alert.setTitle("Information");
            alert.show();


            nomTfd.setText(user.getNom());
            prenomTfd.setText(user.getPrenom());
            phoneTfd.setText(user.getTel());
            emailTfd.setText(user.getEmail());
            villeTfd.setText(user.getVille());
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomTfd.setText(user.getNom());
        prenomTfd.setText(user.getPrenom());
        phoneTfd.setText(user.getTel());
        emailTfd.setText(user.getEmail());
        villeTfd.setText(user.getVille());
    }
}
