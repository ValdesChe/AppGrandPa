package model;

public class Medicament {
    private int id_med;
    private String intitule_med;
    private int quantite_med;
    private int dose;
    private int nbr_fois;

    public Medicament(int id_med, String intitule_med, int quantite_med, int dose, int nbr_fois) {
        this.id_med = id_med;
        this.intitule_med = intitule_med;
        this.quantite_med = quantite_med;
        this.dose = dose;
        this.nbr_fois = nbr_fois;
    }

    public int getId_med() {
        return id_med;
    }

    public void setId_med(int id_med) {
        this.id_med = id_med;
    }

    public String getIntitule_med() {
        return intitule_med;
    }

    public void setIntitule_med(String intitule_med) {
        this.intitule_med = intitule_med;
    }

    public int getQuantite_med() {
        return quantite_med;
    }

    public void setQuantite_med(int quantite_med) {
        this.quantite_med = quantite_med;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getNbr_fois() {
        return nbr_fois;
    }

    public void setNbr_fois(int nbr_fois) {
        this.nbr_fois = nbr_fois;
    }
}
