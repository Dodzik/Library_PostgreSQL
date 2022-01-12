package org.example.domain;

import java.util.List;

public interface IPersistenceHandler {

    boolean checkAdmin(String login, String haslo);

    public List<Friend> getFriends();

    boolean createRezerwacjaAdmin(Rezerwacja rezerwacja);

    public boolean createFriend(Friend friend);

    public List<Klient> getKlienci();

    void setTemp(Integer temp);

    public boolean checkClient(String email, String haslo);

    public List<Ksiazka> getKsiazki();

    public String getGatunekId(Integer gatunek_id);

    public String getWydawnictwoById(Integer wydownictwo_id);

    Klient getKlientById(Integer klient_id);

    public List<Rezerwacja> getRezerwacjeKlient();

    void deleteKlient(Integer id_klient);

    boolean createKlient(Klient klient);

    void deleteRezerwacjeKlienta(Integer id_klient);

    List<Rezerwacja> getRezerwacje();

    void deleteAutor(Integer id_autor);

    void deleteKsiazka(Integer id_ksiazka);

    boolean createAutor(Autor Autor);

    boolean createKsiazka(Ksiazka ksiazka);

    List<Ksiazka> getKsiazkiAutora();

    Ksiazka getKsiazkaById(Integer id);

    List<Autor> getAutorzy();

    List<Stanowisko> getStanowiska();

    void deleteStanowisko(Integer id_stanowisko);

    boolean createStanowisko(Stanowisko stanowisko);

    public String getNazwaKsiazka(Integer id_ksiazka);

    public Klient getKlientInformacje();

    Pracownik getPracownkInformacje();

    public Dane getKlientDane(Integer id_klient);

    Stanowisko getStanowiskoPracownik(Integer id_pracownik);

    public boolean createRezerwacja(Rezerwacja rezerwacja);

    public Integer getMaxIndexRezerwacje();

    Integer getMaxIndexKlienci();

    Integer getMaxIndexStanowiska();

    Integer getMaxIndexAutorzy();

    Integer getMaxIndexKsiazki();

    public Integer getKsiazkaId(String tytul);

    public Integer getKlientId(String imie, String nazwisko);

    Integer getMaxIndexDane();

    List<Dane> getDane();

    void deleteDane(Integer id_dane);

    boolean createDane(Dane dane);
}
