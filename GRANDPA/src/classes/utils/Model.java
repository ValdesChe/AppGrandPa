

//Packages and Imports
package classes.utils;

/**
 *
 * @author Karis
 */
public class Model {
    private final static Model instance = new Model();

    public static Model getInstance() {
        return instance;
    }
    
    // for adding/editing events
    public int event_day;
    public int event_month;
    public int event_year;

    public int event_id;
    public String event_subject;
    public String event_description;
    public String event_hour;
    public String event_minutes;

    // for the year and month the user has open, is "viewing"
    public int viewing_month;
    public int viewing_year;

    //Function that returns a month Index based on the given month name
    public int getMonthIndex(String month){
        switch (month)
        {    
            case "Janvier":
                return 0;
            case "Fevrier":
                return 1;
            case "Mars":
                return 2;
            case "Avril":
                return 3;
            case "Mai":
                return 4;
            case "Juin":
                return 5;
            case "Juillet":
                return 6;
            case "Aout":
                return 7;
            case "Septembre":
                return 8;
            case "Octobre":
                return 9;
            case "Novembre":
                return 10;
            case "Decembre":
                return 11;
        }
        return 0;
    }

    //Function that returns a month Index based on the given month name
    public String getMonthName(int monthIndex){
        switch (monthIndex)
        {
            case 0:
                return "Janvier";
            case 1:
                return "Février";
            case 2:
                return "Mars";
            case 3:
                return "Aril";
            case 4:
                return "Mai";
            case 5:
                return "Juin";
            case 6:
                return "Juillet";
            case 7:
                return "Aout";
            case 8:
                return "Septembre";
            case 9:
                return "Octobre";
            case 10:
                return "Novembre";
            case 11:
                return "Decembre";
        }
        return "Janvier";
    }



    
}
