package controllers;

import classes.database.DatabaseConnector;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactsController extends Controller implements Initializable {

   // @FXML ListView<String> listContacts;

    @FXML
    Button enregistrerBtn, addBtn;
   @FXML
   TextField nomTfd, phoneTfd, emailTfd, prenomTfd, villeTfd;

    @FXML
    TableView<Contact> contactsTable;

    @FXML
    TableColumn<Contact, String> nomClmn, phoneClmn, emailClmn, prenomClmn, villeClmn;

    ObservableList<Contact> listContacts ;

    DatabaseConnector db ;




    public void addContact() throws SQLException{

        if (nomTfd.getText().equals("") || phoneTfd.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,"");

            alert.setTitle("Erreur.");
            alert.setHeaderText("Nom et numéro de téléphone sont obligatoires!");

            alert.show();
        }
        else {

            //on ajoute le noouveau contact dans la base de donnée
            db.executeAction("INSERT INTO contact(nom, prenom, tel, email, ville) values('"+nomTfd.getText()+"', '"+prenomTfd.getText()+"', '"+ phoneTfd.getText()+"', '"+emailTfd.getText()+"', '"+villeTfd.getText()+"')");

            //on recupère l'id du dernier contact.
            ResultSet resultSet = db.executeQuery("SELECT id_contact from contact where nom = '"+nomTfd.getText()+"' and tel =  '"+ phoneTfd.getText()+"' ");

            Alert confirmAjout = new Alert(Alert.AlertType.INFORMATION, "");
            confirmAjout.setHeaderText("Contact ajouté avec succes!");
            confirmAjout.setTitle("Information");
            confirmAjout.show();
            //On insert le nouveau contact dans le table/ la liste des contacts.
            while (resultSet.next()){
                listContacts.add(
                        new Contact(
                                resultSet.getInt("id_contact"),
                                nomTfd.getText(),
                                prenomTfd.getText(),
                                phoneTfd.getText(),
                                emailTfd.getText(),
                                villeTfd.getText()
                        )
                );

            }

            contactsTable.setItems(listContacts);

            nomTfd.clear();
            prenomTfd.clear();
            phoneTfd.clear();
            emailTfd.clear();
            villeTfd.clear();



        }



    }



    public ObservableList<Contact> getContacts() throws SQLException {
        db = new DatabaseConnector();
        listContacts = FXCollections.observableArrayList();

         ResultSet resultSet = db.executeQuery("SELECT * FROM contact");
         while (resultSet.next())
         {
             if(resultSet.getString("nom").equals("") && resultSet.getString("prenom").equals("") && resultSet.getString("tel").equals("") )
             {
                 db.executeAction("DELETE FROM contact WHERE id_contact="+resultSet.getInt("id_contact"));
             }
             else {
                 listContacts.add(new Contact(resultSet.getInt("id_contact"),
                                              resultSet.getString("nom"),
                                              resultSet.getString("prenom"),
                                              resultSet.getString("tel"),
                                              resultSet.getString("email"),
                                              resultSet.getString("ville")));
             }
         }


        return listContacts;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            contactsTable.setItems(getContacts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //contactsTable.getColumns().addAll(nomClmn, prenomClmn, phoneClmn, emailClmn, villeClmn);


    }


    public void modifierContact(ActionEvent actionEvent) {
        Contact contactSelected = contactsTable.getSelectionModel().getSelectedItem();

        nomTfd.setText(contactSelected.getNom());
        prenomTfd.setText(contactSelected.getPrenom());
        phoneTfd.setText(contactSelected.getTel());
        emailTfd.setText(contactSelected.getEmail());
        villeTfd.setText(contactSelected.getVille());


        enregistrerBtn.setVisible(true);

        addBtn.setVisible(false);







    }

    public void supprimerContact(ActionEvent actionEvent) {
        ObservableList<Contact> allContacts;
        Contact contactSelected;

        allContacts = contactsTable.getItems();
        contactSelected = contactsTable.getSelectionModel().getSelectedItem();


        if (db.executeAction("DELETE FROM contact WHERE id_contact="+contactSelected.getId_contact()))
        {
            allContacts.remove(contactSelected);

            Alert confirmSuppression = new Alert(Alert.AlertType.INFORMATION, "");
            confirmSuppression.setHeaderText("Contact supprimé avec succes!");
            confirmSuppression.setTitle("Information");
            confirmSuppression.show();
        }
        else
        {
            Alert confirmSuppression = new Alert(Alert.AlertType.ERROR, "");
            confirmSuppression.setHeaderText("Une erreur s'est produite, veuillez reessayer!");
            confirmSuppression.setTitle("Information");
            confirmSuppression.show();
        }
    }

    public void enregistrerModification(ActionEvent actionEvent) {


        if (nomTfd.getText().equals("") || phoneTfd.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "");
            alert.setHeaderText("Nom et numéro de téléphone ne peuvent pas etre vide!!");
            alert.setTitle("Information");
            alert.show();
        }else {
            //on recupère l'id du contact selectionné.
            ResultSet resultSet = db.executeQuery("SELECT id_contact from contact where nom = '" + nomTfd.getText() + "' and tel ='" + phoneTfd.getText() + "' ");
            int id = 0;
            try {
                while (resultSet.next())
                {
                    id=resultSet.getInt("id_contact");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            db.executeAction("UPDATE contact SET nom='" + nomTfd.getText() +
                    "', prenom='" + prenomTfd.getText() +
                    "', tel='" + phoneTfd.getText() +
                    "', email='" + emailTfd.getText() +
                    "', ville='" + villeTfd.getText() + "' WHERE id_contact=" + id);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "");
            alert.setHeaderText("Contact modifier avec succès, veuiller relancer la fénètre!!");
            alert.setTitle("Information");
            alert.show();

            nomTfd.clear();
            prenomTfd.clear();
            phoneTfd.clear();
            emailTfd.clear();
            villeTfd.clear();

            enregistrerBtn.setVisible(false);
            addBtn.setVisible(true);

            //TO DO: On doit recharger la page pour afficher les modifications.



        }


    }
}
