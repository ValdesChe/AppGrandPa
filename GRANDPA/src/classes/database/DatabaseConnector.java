package classes.database;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by valde on 2019-01-04.
 */
public class DatabaseConnector {
    private static DatabaseConnector handler;

    private static final String DB_URL = "jdbc:derby:grandPaDatabase;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;

    //Variable that controls whether or not the tables have to be created and populated
    private static boolean tablesAlreadyExist = false;

    public static String [] hoursEvent =
            {"01H", "02H", "03H", "04H", "05H", "06H", "07H", "08H", "09H", "10H", "11H",
                    "12H", "13H", "14H", "15H", "16H", "17H", "18H", "19H",
                    "20H", "21H", "22H", "23H", "24H" };


    public static String [] minutesEvent =
            {"00MIN", "01MIN", "02MIN", "03MIN", "04MIN", "05MIN",
                    "06MIN", "07MIN", "08MIN", "09MIN", "10MIN",
                    "11MIN", "12MIN", "13MIN", "14MIN", "15MIN",
                    "16MIN", "17MIN", "18MIN", "19MIN", "20MIN",
                    "21MIN", "22MIN", "23MIN", "24MIN", "25MIN",
                    "26MIN", "27MIN", "28MIN", "29MIN", "30MIN",
                    "31MIN", "32MIN", "33MIN", "34MIN", "35MIN",
                    "36MIN", "37MIN", "38MIN", "39MIN", "40MIN",
                    "41MIN", "42MIN", "43MIN", "44MIN", "45MIN",
                    "46MIN", "47MIN", "48MIN",
                    "49MIN", "50MIN", "51MIN", "52MIN", "53MIN", "54MIN",
                    "55MIN", "56MIN", "57MIN", "58MIN", "59MIN",  };


    public static String[] getHoursEvent() {
        return hoursEvent;
    }

    public static String[] getMinutesEvent() {
        return minutesEvent;
    }

    //Constructor
    public DatabaseConnector() {

        //call to createConnection method that creates the connection between the database and the Java application
        createConnection();

        //checks if tables have been already created by an instantatiation of another object in the program, and if
        //the tables have not being created, then they are created and filled with the correspondent default records
        if (tablesAlreadyExist)
        {
            System.out.println("Tables already exist, so connection was the only thing created and now you are ready to go!");
        }
        else {

            // Creates all tables for the database
            createEventsTable();
            createContactTable();
            createMedicamentTable();

            //Switched boolean variable tablesAlreadyExist to true because tables were just created
            tablesAlreadyExist = true;

            System.out.println("the static variable tablesAlreadyExist was changed to true. THEREFORE, NO other table should try to be created");


        }
    }
    //***************************************************************************************************************************************************************

    //***************************************************************************************************************************************************************
    //Create Connection between Java Application and the JDBC
    void createConnection() {

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    //***************************************************************************************************************************************************************
    //***************************************************************************************************************************************************************
    //**************************   Table des evenements  ***********************************************
    // Function that creates EVENTS Table
    void createEventsTable(){

        String TableName = "evenement";
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbs = conn.getMetaData();
            ResultSet listOfTables = dbs.getTables(null,null, TableName.toUpperCase(), null);

            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " already exists. Ready to go!");
            }
            else {
                String query1 = "CREATE TABLE " + TableName + "("
                        + "id_event integer not null primary key\n" +
                        "        GENERATED ALWAYS AS IDENTITY\n" +
                        "        (START WITH 1, INCREMENT BY 1),\n"
                        + "title VARCHAR(100) not null ,\n"
                        + "description VARCHAR(255) ,\n"
                        + "dateEvent date not null,\n"
                        + "heureEvent VARCHAR(10) not null\n"
                        + ")";
                stmt.execute(query1);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
    }

    //***************************************************************************************************************************************************************
    //***************************************************************************************************************************************************************
    //**************************   Table des Contacts  ***********************************************/
    // Function that creates CONTACTS Table
    void createContactTable(){

        String TableName = "contact";
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);

            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " already exists. Ready to go!");
            }
            else {
                String query1 = "CREATE TABLE " + TableName + "("
                        + "id_contact integer not null primary key\n" +
                        "        GENERATED ALWAYS AS IDENTITY\n" +
                        "        (START WITH 1, INCREMENT BY 1),\n"
                        + "nom varchar(30) not null,\n"
                        + "prenom varchar(30),\n"
                        + "tel  varchar(30),\n"
                        + "email  varchar(30),\n"
                        + "ville  varchar(30)\n"
                        + ")";
                stmt.execute(query1);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
    }



    //***************************************************************************************************************************************************************
    //***************************************************************************************************************************************************************
    //**************************   Table des Contacts  ***********************************************/
    // Function that creates CONTACTS Table
    void createMedicamentTable(){

        String TableName = "medicament";
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet listOfTables = dbm.getTables(null,null, TableName.toUpperCase(), null);

            if (listOfTables.next()) {
                System.out.println("Table " + TableName + " already exists. Ready to go!");
            }
            else {
                String query1 = "CREATE TABLE " + TableName + "("
                        + "id_medicament integer not null primary key\n" +
                        "        GENERATED ALWAYS AS IDENTITY\n" +
                        "        (START WITH 1, INCREMENT BY 1),\n"
                        + "intitule varchar(30) not null,\n"
                        + "quantite integer , \n"
                        // dose  = dose totale par jour
                        + "dose  integer,\n"
                        + "nbr_fois int\n"
                        + ")";
                stmt.execute(query1);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- setupDatabase");
        } finally {
        }
    }


    //***************************************************************************************************************************************************************
    //***************************************************************************************************************************************************************

    //***************************************************************************************************************************************************************
    //Function that checks if a table in the database is empty (has no records), and return a boolean values based on the checking result
    boolean checkIfTableIsEmpty(String tableName) {
        boolean checkingResult = false;
        try {
            stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("SELECT * FROM " + tableName);
            while (res.next()){
                checkingResult = true;
                break;
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage() + "--- checking Table failed/error");
            return false;
        } finally {
        }
        return checkingResult;
    }


    //***************************************************************************************************************************************************************

    //*******************************************
    //Function that executes an insertion, deletion, or update query
    public boolean executeAction(String query2) {
        try {
            stmt = conn.createStatement();
            stmt.execute(query2);
            return true;
        }
        catch (SQLException ex) {
            System.out.println("Exception at executeQuery:dataHandler  --> ERROR: " + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }

    //*****************************************************************************************************************************
    //Function that executes a SELECT query and returns the requested values/data from the database
    public ResultSet executeQuery(String query) {
        System.out.println("DB_LOG:" + query );
        ResultSet result;

        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at executeQuery:dataHandler --> ERROR: " + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }

        return result;
    }
}
