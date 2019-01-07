package controllers;

import classes.database.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Medicament;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class PharmacieControler extends Controller implements Initializable{

    @FXML
    TextField nomMedTfd, qtiteMedTfd, doseMedTfd, nbreFoisTfd;

    @FXML
    Button addBtn, saveBtn;

    @FXML
    TableView<Medicament> medTable;

    @FXML
    TableColumn<Medicament, String> nomMedClmn;

    @FXML
    TableColumn<Medicament, Integer> qtiteMedClmn, doseMedClmn, nbrFoisClmn;


    ObservableList<Medicament> listMedicaments ;

    DatabaseConnector db ;




    public void addMedicament(ActionEvent actionEvent) throws SQLException {

        if (nomMedTfd.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,"");

            alert.setTitle("Erreur.");
            alert.setHeaderText("Veuillez entrer le nom du medicament!");

            alert.show();
        }
        else {


            //on ajoute le noouveau Medicament dans la base de donnée



            db.executeAction("INSERT INTO medicament(intitule, quantite, dose, nbr_fois) values('"+nomMedTfd.getText()+"', "+
                    Integer.parseInt(qtiteMedTfd.getText())+", "+ Integer.parseInt(doseMedTfd.getText())+", "+
                    Integer.parseInt(nbreFoisTfd.getText())+")");
                //on recupère l'id du dernier contact.
                ResultSet resultSet = db.executeQuery("SELECT id_medicament from medicament where intitule = '"+nomMedTfd.getText()+"' and quantite = "+ Integer.parseInt(qtiteMedTfd.getText()));

                Alert confirmAjout = new Alert(Alert.AlertType.INFORMATION, "");
                confirmAjout.setHeaderText("Médicament ajouté avec succes!");
                confirmAjout.setTitle("Information");
                confirmAjout.show();
                //On insert le nouveau contact dans le table/ la liste des contacts.
                while (resultSet.next()) {
                    listMedicaments.add(
                            new Medicament(
                                    resultSet.getInt("id_medicament"),
                                    nomMedTfd.getText(),
                                    Integer.parseInt(qtiteMedTfd.getText()),
                                    Integer.parseInt(doseMedTfd.getText()),
                                    Integer.parseInt(nbreFoisTfd.getText())
                            )
                    );

            }

            medTable.setItems(listMedicaments);

            nomMedTfd.clear();
            qtiteMedTfd.clear();
            doseMedTfd.clear();
            nbreFoisTfd.clear();


            }





    }



    public ObservableList<Medicament> getMedicaments() throws SQLException {
        db = new DatabaseConnector();
        listMedicaments = FXCollections.observableArrayList();

        ResultSet resultSet = db.executeQuery("SELECT * FROM medicament");
        while (resultSet.next())
        {
            listMedicaments.add(new Medicament(resultSet.getInt("id_medicament"),
                        resultSet.getString("intitule"),
                        resultSet.getInt("quantite"),
                        resultSet.getInt("dose"),
                        resultSet.getInt("nbr_fois")));


        }


        return listMedicaments;
    }







    public void modifierMedicament(ActionEvent actionEvent) {

        Medicament medSelected = medTable.getSelectionModel().getSelectedItem();

        nomMedTfd.setText(medSelected.getIntitule_med());
        qtiteMedTfd.setText(String.valueOf(medSelected.getQuantite_med()));
        doseMedTfd.setText(String.valueOf(medSelected.getDose()));
        nbreFoisTfd.setText(String.valueOf(medSelected.getNbr_fois()));


        saveBtn.setVisible(true);

        addBtn.setVisible(false);
    }

    public void supprimerMedicament(ActionEvent actionEvent) {

        ObservableList<Medicament> allMedicaments;
        Medicament medSelected;

        allMedicaments = medTable.getItems();
        medSelected = medTable.getSelectionModel().getSelectedItem();


        if (db.executeAction("DELETE FROM medicament WHERE id_medicament="+medSelected.getId_med()))
        {
            allMedicaments.remove(medSelected);

            Alert confirmSuppressionMed = new Alert(Alert.AlertType.INFORMATION, "");
            confirmSuppressionMed.setHeaderText("Médicament supprimé avec succes!");
            confirmSuppressionMed.setTitle("Information");
            confirmSuppressionMed.show();
        }
        else
        {
            Alert confirmSuppression = new Alert(Alert.AlertType.ERROR, "");
            confirmSuppression.setHeaderText("Une erreur s'est produite, veuillez reessayer!");
            confirmSuppression.setTitle("Information");
            confirmSuppression.show();
        }
    }

    public void saveMedicament(ActionEvent actionEvent) {

        if (nomMedTfd.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING, "");
            alert.setHeaderText("Verifier le nom du medicament!!");
            alert.setTitle("Information");
            alert.show();
        }else {
            //on recupère l'id du medicament selectionné.
            ResultSet resultSet = db.executeQuery("SELECT id_medicament from medicament where intitule = '" + nomMedTfd.getText() + "'");
            int id = 0;
            try {
                while (resultSet.next())
                {
                    id=resultSet.getInt("id_medicament");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            db.executeAction("UPDATE medicament SET intitule='" + nomMedTfd.getText() +
                    "', quantite=" + Integer.parseInt(qtiteMedTfd.getText()) +
                    ", dose=" + Integer.parseInt(doseMedTfd.getText()) +
                    ", nbr_fois=" + Integer.parseInt(nbreFoisTfd.getText()) + " WHERE id_medicament=" + id);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "");
            alert.setHeaderText("Médicament modifié avec succès, veuiller relancer la fénètre!!");
            alert.setTitle("Information");
            alert.show();

            nomMedTfd.clear();
            qtiteMedTfd.clear();
            doseMedTfd.clear();
            nbreFoisTfd.clear();

            saveBtn.setVisible(false);
            addBtn.setVisible(true);

            //TO DO: On doit recharger la page pour afficher les modifications.



        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            medTable.setItems(getMedicaments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
