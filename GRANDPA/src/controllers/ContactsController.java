package controllers;

import classes.database.DatabaseConnector;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
   TextField nomTfd, phoneTfd, emailTfd, prenomTfd, villeTfd;

    @FXML
    TableView<Contact> contactsTable;

    @FXML
    TableColumn<Contact, String> nomClmn, phoneClmn, emailClmn, prenomClmn, villeClmn;

    ObservableList<Contact> listContacts ;

    DatabaseConnector db ;




    public void addContact() throws SQLException{

        db.executeAction("INSERT INTO contact(nom, prenom, tel, email, ville) values('"+nomTfd.getText()+"', '"+prenomTfd.getText()+"', '"+ phoneTfd.getText()+"', '"+emailTfd.getText()+"', '"+villeTfd.getText()+"')");

        ResultSet resultSet = db.executeQuery("SELECT id_contact from contact where nom = '"+nomTfd.getText()+"' and tel =  '"+ phoneTfd.getText()+"' ");

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

        nomTfd.setText("") ;
        prenomTfd.setText("") ;
        phoneTfd.setText("") ;
        emailTfd.setText("") ;
        villeTfd.setText("");




    }



    public ObservableList<Contact> getContacts() throws SQLException {
        db = new DatabaseConnector();
        listContacts = FXCollections.observableArrayList();
        //db.executeAction("INSERT INTO contact values(1, 'AliTAhir', 'Mohamed', '065487', 'mail@mail', 'Tetouan')");

         ResultSet resultSet = db.executeQuery("SELECT * FROM contact");
         while (resultSet.next())
         {
             if(resultSet.getString("nom").equals("") && resultSet.getString("prenom").equals("") && resultSet.getString("tel").equals("") )
             {
                 db.executeAction("DELETE FROM contact WHERE id_contact="+resultSet.getInt("id_contact"));
                 System.out.println("Hellooooo");
             }
             else {
                 listContacts.add(new Contact(resultSet.getInt("id_contact"), resultSet.getString("nom"), resultSet.getString("prenom"),
                         resultSet.getString("tel"), resultSet.getString("email"), resultSet.getString("ville")));
                 System.out.println(resultSet.getString("nom"));
             }
         }


        //Ajouter des contacts dans la listContacts
       // listContacts.add(new Contact(1, "Ali", "Tahir", "0681920973", "mo.alitahir9@gmail.com", "Tetouan"));
       listContacts.add(new Contact(1, "Ali", "Tahir", "0681920973", "mo.alitahir9@gmail.com", "Tetouan"));
        //Retourner la liste
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


}
