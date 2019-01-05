package model;

public class Contact {
    private int id_contact ;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String ville;

    public Contact(int id_contact, String nom, String prenom, String tel, String email, String ville) {
        this.id_contact = id_contact;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.ville = ville;
    }

    public int getId_contact() {
        return id_contact;
    }

    public void setId_contact(int id_contact) {
        this.id_contact = id_contact;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
