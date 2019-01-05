package model;

import java.util.Date;

public class Evenement {

    private String title_event;
    private String desc_event;
    private String heure_appel;
    private Date date_event;
    private int id_event;

    public Evenement(String title_event, String desc_event, String heure_appel, Date date_event, int id_event) {
        this.title_event = title_event;
        this.desc_event = desc_event;
        this.heure_appel = heure_appel;
        this.date_event = date_event;
        this.id_event = id_event;
    }

    public String getTitle_event() {
        return title_event;
    }

    public void setTitle_event(String title_event) {
        this.title_event = title_event;
    }

    public String getDesc_event() {
        return desc_event;
    }

    public void setDesc_event(String desc_event) {
        this.desc_event = desc_event;
    }

    public String getHeure_appel() {
        return heure_appel;
    }

    public void setHeure_appel(String heure_appel) {
        this.heure_appel = heure_appel;
    }

    public Date getDate_event() {
        return date_event;
    }

    public void setDate_event(Date date_event) {
        this.date_event = date_event;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }
}
