package org.example.domain;

public class Wydawnictwo {
    private Integer id;
    private String nazwa;

    public Wydawnictwo(Integer id, String nazwa) {
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
        return "Wydawnictwo{" +
                "id=" + id +
                ", nazwa='" + nazwa + '\'' +
                '}';
    }
}
