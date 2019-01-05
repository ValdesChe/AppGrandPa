package classes.prayer;

import classes.utils.Prayers;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PrayerFetcher {
    private Prayers prayers = null;
    private final String URL_BASE = "http://muslimsalat.com/";
    private String town = "tetouan";
    public PrayerFetcher(String town) {
        this.town = town;
    }

    public PrayerFetcher() {

    }

    public void fetchPrayers() throws IOException{

        URL obj = new URL(URL_BASE + this.town + ".json");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        // System.out.println("\nSending 'GET' request to URL : " + url);
        // System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String

        String gy = response.toString().replace("\"for\"","\"for_\"");
        // System.out.println(gy);
        //Read JSON response and print

        ObjectMapper objectmapper = new ObjectMapper();
         this.prayers = objectmapper.readValue(gy, Prayers.class);
    }

    public Prayers getPrayers() {
        return prayers;
    }

    public String getURL_BASE() {
        return URL_BASE;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}

