package org.example.domain;

import java.util.List;

public interface IPersistenceHandler {

    public List<Friend> getFriends();

    public boolean createFriend(Friend friend);

//    public List<Klient> getKlienci();

    public boolean checkClient(String email, String haslo);

    public List<Book> getKsiazki();

    public String getGatunek(Integer gatunek_id);

    public String getWydawnictwo(Integer wydownictwo_id);

    public List<Rezerwacja> getRezerwacjeKlient();

    public String getNazwaKsiazka(Integer id_ksiazka);

    public Klient getKlientInformacje();

    public Dane getKlientDane(Integer id_klient);
}
