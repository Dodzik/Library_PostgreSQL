package org.example.domain;


import java.sql.Date;

public class Rezerwacja {

    private Integer id_rezerwacje;
    private Integer ksiazka_id;
    private Integer klient_id;
    private Date date;

    public Rezerwacja(Integer id_rezerwacje, Integer ksiazka_id, Integer klient_id, Date date) {
        this.id_rezerwacje = id_rezerwacje;
        this.ksiazka_id = ksiazka_id;
        this.klient_id = klient_id;
        this.date = date;
    }

    public Integer getId_rezerwacje() {
        return id_rezerwacje;
    }

    public Integer getKsiazka_id() {
        return ksiazka_id;
    }

    public Integer getKlient_id() {
        return klient_id;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Rezerwacja{" +
                "id_rezerwacje=" + id_rezerwacje +
                ", ksiazka_id=" + ksiazka_id +
                ", klient_id=" + klient_id +
                ", date=" + date +
                '}';
    }
}
