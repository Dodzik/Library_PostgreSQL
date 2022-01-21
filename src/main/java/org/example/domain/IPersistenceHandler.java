package org.example.domain;

import java.sql.Date;
import java.util.List;

public interface IPersistenceHandler {

    /**
     *
     * @param login
     * @param haslo
     * @return
     */
    boolean checkAdmin(String login, String haslo);

    /**
     *
     * @param rezerwacja
     * @return
     */
    boolean createRezerwacjaAdmin(Rezerwacja rezerwacja);

    /**
     *
     * @return
     */
    public List<Klient> getKlienci();

    /**
     *
     * @param temp
     */
    void setTemp(Integer temp);

    /**
     *
     * @param email
     * @param haslo
     * @return
     */
    public boolean checkClient(String email, String haslo);

    /**
     *
     * @return
     */
    public List<Ksiazka> getKsiazki();

    /**
     *
     * @param gatunek_id
     * @return
     */
    public String getGatunekId(Integer gatunek_id);

    /**
     *
     * @param wydownictwo_id
     * @return
     */
    public String getWydawnictwoById(Integer wydownictwo_id);

    /**
     *
     * @param klient_id
     * @return
     */
    Klient getKlientById(Integer klient_id);

    /**
     *
     * @return
     */
    public List<Rezerwacja> getRezerwacjeKlient();

    List<Ksiazka> sortKsiazki(String str);

    /**
     *
     * @param id_klient
     */
    void deleteKlient(Integer id_klient);

    void deleteWypozyczeniaKlienta(Integer id_klient);

    /**
     *
     * @param klient
     * @return
     */
    boolean createKlient(Klient klient);

    /**
     *
     * @param id_klient
     */
    void deleteRezerwacjeKlienta(Integer id_klient);

    /**
     *
     * @return
     */
    List<Rezerwacja> getRezerwacje();

    /**
     *
     * @param id_autor
     */
    void deleteAutor(Integer id_autor);

    /**
     *
     * @param id_ksiazka
     */
    void deleteKsiazka(Integer id_ksiazka);

    /**
     *
     * @param Autor
     * @return
     */
    boolean createAutor(Autor Autor);

    /**
     *
     * @param ksiazka
     * @return
     */
    boolean createKsiazka(Ksiazka ksiazka);

    /**
     *
     * @return
     */
 List<Ksiazka> getKsiazkiAutora();

    /**
     *
     * @param id
     * @return
     */
    Ksiazka getKsiazkaById(Integer id);

    /**
     *
     * @return
     */
    List<Autor> getAutorzy();

    /**
     *
     * @return
     */
    List<Stanowisko> getStanowiska();

    /**
     *
     * @param id_stanowisko
     */
    void deleteStanowisko(Integer id_stanowisko);

    /**
     *
     * @param stanowisko
     * @return
     */
    boolean createStanowisko(Stanowisko stanowisko);

    /**
     *
     * @param id_ksiazka
     * @return
     */
    public String getNazwaKsiazka(Integer id_ksiazka);

    /**
     *
     * @return
     */
    public Klient getKlientInformacje();

    /**
     *
     * @return
     */
    Pracownik getPracownkInformacje();

    /**
     *
     * @param id_klient
     * @return
     */
    public Dane getKlientDane(Integer id_klient);

    /**
     *
     * @param id_pracownik
     * @return
     */
    Stanowisko getStanowiskoPracownik(Integer id_pracownik);

    /**
     *
     * @param rezerwacja
     * @return
     */
    public boolean createRezerwacja(Rezerwacja rezerwacja);

    /**
     *
     * @return
     */
    public Integer getMaxIndexRezerwacje();

    /**
     *
     * @return
     */
    Integer getMaxIndexKlienci();

    /**
     *
     * @return
     */
    Integer getMaxIndexStanowiska();

    /**
     *
     * @return
     */
    Integer getMaxIndexAutorzy();

    /**
     *
     * @return
     */
    Integer getMaxIndexKsiazki();

    /**
     *
     * @param tytul
     * @return
     */
    public Integer getKsiazkaId(String tytul);

    /**
     *
     *
     * @param imie
     * @param nazwisko
     * @return
     */
    public Integer getKlientId(String imie, String nazwisko);

    /**
     *
     *
     * @return
     */
    Integer getMaxIndexDane();

    /**
     *
     * @return
     */
    List<Dane> getDane();

    /**
     *
     * @param id_dane
     */
    void deleteDane(Integer id_dane);

    /**
     *
     * @param id_pracownik
     * @param id_klient
     * @param id_ksiazka
     */
    void deleteWypozyczenie(Integer id_pracownik, Integer id_klient, Integer id_ksiazka);

    /**
     *
     * @param dane
     * @return
     */
    boolean createDane(Dane dane);

    /**
     *
     * @return
     */
    List<Wypozyczenie> getWypozyczenia();

    /**
     *
     * @param id_autor
     * @param id_ksiazka
     */
    void deleteAutorzyKsiazek(Integer id_autor, Integer id_ksiazka);

    /**
     *
     * @param wypozyczenie
     * @return
     */
    boolean createWypozyczenie(Wypozyczenie wypozyczenie);

    /**
     *
     *
     * @param id_pracownik
     * @param id_klient
     * @param id_ksiazka
     * @param data_oddania
     * @return
     */
    boolean updateWypozyczenie(Integer id_pracownik, Integer id_klient, Integer id_ksiazka, Date data_oddania);

    /**
     *
     * @return
     */
    List<AutorzyKsiazek> getAutorzyKsiazek();

    /**
     *
     * @param id_wydawnictwo
     */
    void deleteWydawnictwo(Integer id_wydawnictwo);

    /**
     *
     * @param autorzyKsiazek
     * @return
     */
    boolean createAutorzyKsiazek(AutorzyKsiazek autorzyKsiazek);

    /**
     *
     * @return
     */
    List<Wydawnictwo> getWydawnictwa();

    /**
     *
     * @return
     */
    List<Gatunek> getGatunki();

    /**
     *
     * @return
     */
    Integer getMaxIndexWydawnictwa();

    /**
     *
     * @param wydawnictwo
     * @return
     */
    boolean createWydawnictwo(Wydawnictwo wydawnictwo);

    /**
     *
     * @return
     */
    Integer getMaxIndexGatunki();

    /**
     *
     * @param id_gatunek
     */
    void deleteGatunek(Integer id_gatunek);

    /**
     *
     * @param id_pracownik
     */
    void deletePracownik(Integer id_pracownik);

    /**
     *
     * @param gatunek
     * @return
     */
    boolean createGatunek(Gatunek gatunek);

    /**
     *
     * @return
     */
    List<Pracownik> getPracownicy();

    /**
     *
     * @return
     */
    Integer getMaxIndexPracownicy();

    /**
     *
     * @param pracownik
     * @return
     */
    boolean createPracownik(Pracownik pracownik);
}
