package org.example.domain;

import java.util.List;

public interface IPersistenceHandler {

    public List<Friend> getFriends();

    public boolean createFriend(Friend friend);

//    public List<Klient> getKlienci();

    void setTemp(Integer temp);

    public boolean checkClient(String email, String haslo);

    public List<Book> getKsiazki();

    public String getGatunekId(Integer gatunek_id);

    public String getWydawnictwoById(Integer wydownictwo_id);

    Klient getKlientById(Integer klient_id);

    public List<Rezerwacja> getRezerwacjeKlient();

    List<Book> getKsiazkiAutora();

    Book getKsiazkaById(Integer id);

    List<Autor> getAutorzy();

    public String getNazwaKsiazka(Integer id_ksiazka);

    public Klient getKlientInformacje();

    public Dane getKlientDane(Integer id_klient);

    public boolean createRezerwacja(Rezerwacja rezerwacja);

    public Integer getMaxIndexRezerwacje();

    public Integer getKsiazkaId(String tytul);

    public Integer getKlientId(String imie, String nazwisko);
}
