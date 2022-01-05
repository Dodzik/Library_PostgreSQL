package org.example.domain;

public class Pracownik {
    private Integer id;
    private Integer stanowisko_id;
    private String login;
    private String haslo;

    public Pracownik(Integer id, Integer stanowisko_id, String login, String haslo) {
        this.id = id;
        this.stanowisko_id = stanowisko_id;
        this.login = login;
        this.haslo = haslo;
    }

    public Integer getId() {
        return id;
    }

    public Integer getStanowisko_id() {
        return stanowisko_id;
    }

    public String getLogin() {
        return login;
    }

    public String getHaslo() {
        return haslo;
    }

    @Override
    public String toString() {
        return "Pracownik{" +
                "id=" + id +
                ", stanowisko_id=" + stanowisko_id +
                ", login='" + login + '\'' +
                ", haslo='" + haslo + '\'' +
                '}';
    }
}
