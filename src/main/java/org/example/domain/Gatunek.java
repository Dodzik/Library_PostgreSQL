package org.example.domain;

public class Gatunek {
    private Integer id;
    private String nazwa;

    public Gatunek(Integer id, String nazwa) {
        this.id = id;
        this.nazwa = nazwa;
    }

    public Integer getId() {
        return id;
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return "Gatunki{" +
                "id=" + id +
                ", nazwa='" + nazwa + '\'' +
                '}';
    }
}
