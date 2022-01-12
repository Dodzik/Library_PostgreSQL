package org.example.domain;

public class Ksiazka {
    private Integer id;
    private Integer gatunek_id;
    private Integer wydawnictwo_id;
    private String tytul;
    private Integer liczbaStron;
    private String opis;

    public Ksiazka(Integer id, Integer gatunek_id, Integer wydawnictwo_id, String tytul, Integer liczbaStron, String opis) {
        this.id = id;
        this.gatunek_id = gatunek_id;
        this.wydawnictwo_id = wydawnictwo_id;
        this.tytul = tytul;
        this.liczbaStron = liczbaStron;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGatunek_id() {
        return gatunek_id;
    }

    public Integer getWydawnictwo_id() {
        return wydawnictwo_id;
    }

    public String getTytul() {
        return tytul;
    }

    public Integer getLiczbaStron() {
        return liczbaStron;
    }

    public String getOpis() {
        return opis;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", gatunek_id=" + gatunek_id +
                ", wydawnictwo_id=" + wydawnictwo_id +
                ", tytul='" + tytul + '\'' +
                ", liczbaStron=" + liczbaStron +
                ", opis='" + opis + '\'' +
                '}';
    }
}
