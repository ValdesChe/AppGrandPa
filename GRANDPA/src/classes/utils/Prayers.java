package classes.utils;

import java.util.List;

public class Prayers {
    private String title;
    private String link;
    private String address;

    private String map_image;

    private String query;
    private String for_;
    private String method;
    private String prayer_method_name;
    private String daylight;
    private String sealevel;
    private String qibla_direction;
    private Today_Weather today_weather;




    private String city;
    private String timezone;
    private String latitude;
    private String longitude;
    private String state;
    private String postal_code;
    private String country;
    private String country_code;
    private List<Items> items;
    private boolean  status_valid;
    private boolean status_code;
    private String  status_description;



        public Prayers() {

        }


    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getFor_() {
        return for_;
    }

    public void setFor_(String for_) {
        this.for_ = for_;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPrayer_method_name() {
        return prayer_method_name;
    }

    public void setPrayer_method_name(String prayer_method_name) {
        this.prayer_method_name = prayer_method_name;
    }

    public String getDaylight() {
        return daylight;
    }

    public void setDaylight(String daylight) {
        this.daylight = daylight;
    }

    public String getSealevel() {
        return sealevel;
    }

    public void setSealevel(String sealevel) {
        this.sealevel = sealevel;
    }

    public String getQibla_direction() {
        return qibla_direction;
    }

    public void setQibla_direction(String qibla_direction) {
        this.qibla_direction = qibla_direction;
    }

    public Today_Weather getToday_weather() {
        return today_weather;
    }

    public void setToday_weather(Today_Weather today_weather) {
        this.today_weather = today_weather;
    }

    public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getAddress() {
            return address;
        }



        public void setAddress(String address) {
            this.address = address;
        }

    public String getMap_image() {
        return map_image;
    }

    public void setMap_image(String map_image) {
        this.map_image = map_image;
    }

    public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }

    public boolean isStatus_valid() {
        return status_valid;
    }

    public void setStatus_valid(boolean status_valid) {
        this.status_valid = status_valid;
    }

    public boolean isStatus_code() {
        return status_code;
    }

    public void setStatus_code(boolean status_code) {
        this.status_code = status_code;
    }

    public String getStatus_description() {
        return status_description;
    }

    public void setStatus_description(String status_description) {
        this.status_description = status_description;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
}