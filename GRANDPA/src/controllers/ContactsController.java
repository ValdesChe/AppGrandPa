package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ContactsController extends Controller {

    @FXML ListView<String> listContacts;
    @FXML
    TextField nomTfd, phoneTfd, emailTfd, adresseTfd, villeTfd;



    public void addContact(){

        String contact = nomTfd.getText() + " | " + phoneTfd.getText()+" | "+emailTfd.getText()+" | "+adresseTfd.getText()+" | " + villeTfd.getText();

        listContacts.getItems().add(contact);

    }


}
