package org.example.domain;

public class AutorzyKsiazek {
    private Integer autor_id;
    private Integer ksiazka_id;

    public AutorzyKsiazek(Integer autor_id, Integer ksiazka_id) {
        this.autor_id = autor_id;
        this.ksiazka_id = ksiazka_id;
    }

    public Integer getAutor_id() {
        return autor_id;
    }

    public Integer getKsiazka_id() {
        return ksiazka_id;
    }

    @Override
    public String toString() {
        return "AutorzyKsiazek{" +
                "autor_id=" + autor_id +
                ", ksiazka_id=" + ksiazka_id +
                '}';
    }
}
