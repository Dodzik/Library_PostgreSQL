package org.example.domain;

import java.util.Date;

public class Rezerwacja {

    private Integer id_rezerwacje;
    private String ksiazka;
    private String imie;
    private String nazwisko;
    private Date date;

    public Integer getId_rezerwacje() {
        return id_rezerwacje;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public Date getDate() {
        return date;
    }

    public String getKsiazka() {
        return ksiazka;
    }

    public Rezerwacja(Integer id_rezerwacje, String ksiazka, String imie, String nazwisko, Date date) {
        this.id_rezerwacje = id_rezerwacje;
        this.ksiazka = ksiazka;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Rezerwacja{" +
                "id_rezerwacje=" + id_rezerwacje +
                ", ksiazka='" + ksiazka + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", date=" + date +
                '}';
    }
}
