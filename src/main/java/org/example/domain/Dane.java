package org.example.domain;

public class Dane {

    private Integer id;
    private String miasto;
    private String ulica;
    private String nr_domu;
    private String kod_pocztowy;

    public Dane(Integer id, String miasto, String ulica, String nr_domu, String kod_pocztowy) {
        this.id = id;
        this.miasto = miasto;
        this.ulica = ulica;
        this.nr_domu = nr_domu;
        this.kod_pocztowy = kod_pocztowy;
    }

    public Integer getId() {
        return id;
    }

    public String getMiasto() {
        return miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public String getNr_domu() {
        return nr_domu;
    }

    public String getKod_pocztowy() {
        return kod_pocztowy;
    }

    @Override
    public String toString() {
        return "Dane{" +
                "id=" + id +
                ", miasto='" + miasto + '\'' +
                ", ulica='" + ulica + '\'' +
                ", nr_domu='" + nr_domu + '\'' +
                ", kod_pocztowy='" + kod_pocztowy + '\'' +
                '}';
    }
}
