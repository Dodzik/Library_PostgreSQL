package org.example.domain;

public class Klient {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String haslo;


    public Klient(Integer id, String name, String surname, String email, String haslo) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.haslo = haslo;
    }

    public Integer getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }


    public String getHaslo() {
        return haslo;
    }

    @Override
    public String toString() {
        return "Klient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", haslo='" + haslo + '\'' +
                '}';
    }
}
