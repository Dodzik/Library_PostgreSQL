package org.example.domain;

import java.sql.Date;

public class Wypozyczenie {

    private Integer id_pracownik;
    private Integer id_klient;
    private Integer id_ksiazka;
    private Date data_wypozyczenia;
    private Date data_oddania;

    public Wypozyczenie(Integer id_pracownik, Integer id_klient, Integer id_ksiazka, Date data_wypozyczenia, Date data_oddania) {
        this.id_pracownik = id_pracownik;
        this.id_klient = id_klient;
        this.id_ksiazka = id_ksiazka;
        this.data_wypozyczenia = data_wypozyczenia;
        this.data_oddania = data_oddania;
    }

    public Integer getId_pracownik() {
        return id_pracownik;
    }

    public Integer getId_klient() {
        return id_klient;
    }

    public Integer getId_ksiazka() {
        return id_ksiazka;
    }

    public Date getData_wypozyczenia() {
        return data_wypozyczenia;
    }

    public Date getData_oddania() {
        return data_oddania;
    }

    @Override
    public String toString() {
        return "Wypozyczenie{" +
                "id_pracownik=" + id_pracownik +
                ", id_klient=" + id_klient +
                ", id_ksiazka=" + id_ksiazka +
                ", data_wopozyczenia=" + data_wypozyczenia +
                ", data_oddania=" + data_oddania +
                '}';
    }
}
