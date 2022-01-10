package org.example.domain;

public class Stanowisko {
    private Integer id;
    private String nazwa;

    public Stanowisko(Integer id, String nazwa) {
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
        return "Stanowisko{" +
                "id=" + id +
                ", nazwa='" + nazwa + '\'' +
                '}';
    }
}
