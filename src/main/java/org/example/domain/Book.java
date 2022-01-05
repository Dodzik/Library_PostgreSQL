package org.example.domain;

public class Book {
    private Integer id;
    private String gatunek;
    private String wydawnictwo;
    private String tytul;
    private Integer liczbaStron;
    private String opis;

    public Book(Integer id, String gatunek, String wydawnictwo, String tytul, Integer liczbaStron, String opis) {
        this.id = id;
        this.gatunek = gatunek;
        this.wydawnictwo = wydawnictwo;
        this.tytul = tytul;
        this.liczbaStron = liczbaStron;
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public String getGatunek() {
        return gatunek;
    }

    public String getWydawnictwo() {
        return wydawnictwo;
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
                ", gatunek_id=" + gatunek +
                ", wydawnictwo_id=" + wydawnictwo +
                ", tytul='" + tytul + '\'' +
                ", liczbaStron=" + liczbaStron +
                ", opis='" + opis + '\'' +
                '}';
    }
}
