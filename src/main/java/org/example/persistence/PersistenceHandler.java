package org.example.persistence;

import org.example.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PersistenceHandler implements IPersistenceHandler {
    private static PersistenceHandler instance;
    private static String url = "abul.db.elephantsql.com";
    private static int port = 5432;
    private static String databaseName = "zwvjtqus";
    private static String username = "zwvjtqus";
    private static String password = "2TxSSPNIOQ_1Lml3bELGFn4ANpmFIOtp";
    private Connection connection = null;


    private PersistenceHandler() {
        initializePostgresqlDatabase();
    }

    public static PersistenceHandler getInstance() {
        if (instance == null) {
            instance = new PersistenceHandler();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
            System.out.println("DB connected");
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) {
                System.exit(-1);
            }
        }
    }

    private String sesjaEmail = "";
    private Integer temp = -1;

    @Override
    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    @Override
    public boolean checkClient(String email, String haslo) {

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM klienci Where email = ? AND haslo = ?");
            return autoryzacja(email, haslo, stmt);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean checkAdmin(String login, String haslo) {

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM pracownicy Where login = ? AND haslo = ?");
            return autoryzacja(login, haslo, stmt);


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    private boolean autoryzacja(String login, String haslo, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, login);
        stmt.setString(2, haslo);
        ResultSet sqlReturnValues = stmt.executeQuery();
        System.out.println("email: " + login + " haslo: " + haslo);

        if (!sqlReturnValues.next()) {
            System.out.println("Nie powodzenie");
            return false;
        } else {
            sesjaEmail = login;
            System.out.println("Powodzenie");
            return true;
        }
    }

    @Override
    public List<Klient> getKlienci() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM klienci");
            ResultSet sqlReturnValues = stmt.executeQuery();
            List<Klient> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Klient(sqlReturnValues.getInt(1), sqlReturnValues.getInt(2),
                        sqlReturnValues.getString(3), sqlReturnValues.getString(4),
                        sqlReturnValues.getString(5), sqlReturnValues.getString(6)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ksiazka> getKsiazki() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ksiazki");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Ksiazka> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Ksiazka(sqlReturnValues.getInt(1), sqlReturnValues.getInt(2),
                        sqlReturnValues.getInt(3), sqlReturnValues.getString(4), sqlReturnValues.getInt(5),
                        sqlReturnValues.getString(6)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteKlient(Integer id_klient) {
        try {
            deleteRezerwacjeKlienta(id_klient);
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM klienci WHERE id_klient=" + id_klient);
            stmt.executeUpdate();
//            deleteRezerwacjeKlienta(id_klient);
            System.out.println("Usunieto klienta");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    @Override
    public boolean createKlient(Klient klient) {
        try {
            PreparedStatement insertKlient;
            if (klient.getId() == null) {
                insertKlient = connection.prepareStatement(
                        "INSERT INTO klienci (id_klient, dane_id_dane, imie, nazwisko, email, haslo) VALUES (?,?,?,?,?,?);");
                insertKlient.setInt(1, getMaxIndexKlienci() + 1);
                insertKlient.setInt(2, klient.getId_dane());
                insertKlient.setString(3, klient.getName());
                insertKlient.setString(4, klient.getSurname());
                insertKlient.setString(5, klient.getEmail());
                insertKlient.setString(6, klient.getHaslo());
            } else {
                insertKlient = connection.prepareStatement(
                        "INSERT INTO klienci (id_klient, dane_id_dane, imie, nazwisko, email, haslo) VALUES (?,?,?,?,?,?);");
                insertKlient.setInt(1, klient.getId());
                insertKlient.setInt(2, klient.getId_dane());
                insertKlient.setString(3, klient.getName());
                insertKlient.setString(4, klient.getSurname());
                insertKlient.setString(5, klient.getEmail());
                insertKlient.setString(6, klient.getHaslo());
            }
            insertKlient.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteRezerwacjeKlienta(Integer id_klient) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM rezerwacje WHERE klienci_id_klient=" + id_klient);
            stmt.executeUpdate();
            System.out.println("Usunieto rezerwacje klienta");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Rezerwacja> getRezerwacje() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM rezerwacje");
            return getRezerwacjas(stmt);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAutor(Integer id_autor) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM autorzy WHERE id_autor=" + id_autor);
            stmt.executeUpdate();
            System.out.println("Usunieto Autora");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createAutor(Autor autor) {
        try {
            PreparedStatement insertAutor = connection.prepareStatement(
                    "INSERT INTO autorzy (id_autor, imie, nazwisko) VALUES (?,?,?);");
            insertAutor.setInt(1, getMaxIndexAutorzy() + 1);
            insertAutor.setString(2, autor.getImie());
            insertAutor.setString(3, autor.getNazwisko());
            insertAutor.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void deleteKsiazka(Integer id_ksiazka) {
        try {
            PreparedStatement stmt2 = connection.prepareStatement("DELETE  FROM autorzy_ksiazki WHERE ksiazki_id_ksiazka=" + id_ksiazka);
            stmt2.executeUpdate();
            PreparedStatement stmt3 = connection.prepareStatement("DELETE FROM rezerwacje WHERE ksiazki_id_ksiazka=" + id_ksiazka);
            stmt3.executeUpdate();
            PreparedStatement stmt4 = connection.prepareStatement("DELETE FROM pracownicy_has_klienci WHERE ksiazki_id_ksiazka=" + id_ksiazka);
            stmt4.executeUpdate();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM ksiazki WHERE id_ksiazka=" + id_ksiazka);
            stmt.executeUpdate();
            System.out.println("Usunieto Ksiazke ze wszystkich tabel");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createKsiazka(Ksiazka ksiazka) {
        try {
            PreparedStatement insertKsiazka = connection.prepareStatement(
                    "INSERT INTO ksiazki (id_ksiazka, gatunki_id_gatunek, wydawnictwa_id_wydawnictwa, tytul, liczba_stron, opis) VALUES (?,?,?,?,?,?)");
            insertKsiazka.setInt(1, getMaxIndexKsiazki() + 1);
            insertKsiazka.setInt(2, ksiazka.getGatunek_id());
            insertKsiazka.setInt(3, ksiazka.getWydawnictwo_id());
            insertKsiazka.setString(4, ksiazka.getTytul());
            insertKsiazka.setInt(5, ksiazka.getLiczbaStron());
            insertKsiazka.setString(6, ksiazka.getOpis());
            insertKsiazka.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Ksiazka> getKsiazkiAutora() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM autorzy_ksiazki WHERE autorzy_id_autor=" + "'" + temp + "'");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Ksiazka> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                Integer autor_id = sqlReturnValues.getInt(2);
                returnValues.add(getKsiazkaById(autor_id));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Ksiazka getKsiazkaById(Integer id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ksiazki WHERE id_ksiazka=" + id);
            ResultSet sqlReturnValues = stmt.executeQuery();

            if (sqlReturnValues.next()) {
                return new Ksiazka(sqlReturnValues.getInt(1), sqlReturnValues.getInt(2),
                        sqlReturnValues.getInt(3), sqlReturnValues.getString(4), sqlReturnValues.getInt(5),
                        sqlReturnValues.getString(6));
            }
            System.out.println("nie ma takiego indeksu");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Autor> getAutorzy() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM autorzy");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Autor> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Autor(sqlReturnValues.getInt(1), sqlReturnValues.getString(2),
                        sqlReturnValues.getString(3)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Stanowisko> getStanowiska() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM stanowiska");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Stanowisko> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Stanowisko(sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteStanowisko(Integer id_stanowisko) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM stanowiska WHERE id_stanowisko=" + id_stanowisko);
            stmt.executeUpdate();
            System.out.println("Usunieto Stanowisko");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createStanowisko(Stanowisko stanowisko) {
        try {
            PreparedStatement insertStanowisko = connection.prepareStatement(
                    "INSERT INTO stanowiska (id_stanowisko, nazwa) VALUES (?,?)");
            insertStanowisko.setInt(1, getMaxIndexStanowiska() + 1);
            insertStanowisko.setString(2, stanowisko.getNazwa());
            insertStanowisko.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getNazwaKsiazka(Integer id_ksiazka) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT ksiazki.tytul FROM ksiazki WHERE ksiazki.id_ksiazka=" + id_ksiazka);
            ResultSet sqlReturnValues = stmt.executeQuery();

            if (sqlReturnValues.next()) {
                return sqlReturnValues.getString(1);
            }
            return "nie ma takiej ksiazki";
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Klient getKlientById(Integer klient_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM klienci WHERE klienci.id_klient=" + klient_id);
            ResultSet sqlReturnValues = stmt.executeQuery();

            if (sqlReturnValues.next()) {
                return new Klient(sqlReturnValues.getInt(1), sqlReturnValues.getInt(2), sqlReturnValues.getString(3),
                        sqlReturnValues.getString(4),
                        sqlReturnValues.getString(5),
                        sqlReturnValues.getString(6));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Rezerwacja> getRezerwacjeKlient() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM klienci WHERE klienci.email=" + "'" + sesjaEmail + "'");
            ResultSet sqlReturnValues = stmt.executeQuery();
            Integer Klient_id = 0;
            while (sqlReturnValues.next()) {
                Klient_id = sqlReturnValues.getInt(1);
            }

            PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM rezerwacje WHERE rezerwacje.klienci_id_klient=" + Klient_id);
            return getRezerwacjas(stmt2);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    private List<Rezerwacja> getRezerwacjas(PreparedStatement stmt2) throws SQLException {
        ResultSet sqlReturnValues2 = stmt2.executeQuery();

        List<Rezerwacja> returnValues = new ArrayList<>();
        while (sqlReturnValues2.next()) {

            returnValues.add(new Rezerwacja(sqlReturnValues2.getInt(1), sqlReturnValues2.getInt(2),
                    sqlReturnValues2.getInt(3),
                    sqlReturnValues2.getDate(4)));
        }
        return returnValues;
    }

    @Override
    public String getGatunekId(Integer gatunek_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT gatunki.nazwa FROM gatunki WHERE gatunki.id_gatunek=" + gatunek_id);
            ResultSet sqlReturnValues = stmt.executeQuery();

            if (sqlReturnValues.next()) {
                return sqlReturnValues.getString(1);
            }
            return "nie ma takiego gatunku";
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public String getWydawnictwoById(Integer wydawnictwo_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT wydawnictwa.nazwa FROM wydawnictwa WHERE wydawnictwa.id_wydawnictwa=" + wydawnictwo_id);
            ResultSet sqlReturnValues = stmt.executeQuery();

            if (sqlReturnValues.next()) {
                return sqlReturnValues.getString(1);
            }
            return "nie ma takiego Wydawnictwa";
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Klient getKlientInformacje() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM klienci WHERE klienci.email=" + "'" + sesjaEmail + "'");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return new Klient(sqlReturnValues.getInt(1), sqlReturnValues.getInt(2), sqlReturnValues.getString(3),
                        sqlReturnValues.getString(4), sqlReturnValues.getString(5), sqlReturnValues.getString(6));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    @Override
    public Dane getKlientDane(Integer id_klient) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dane WHERE dane.id_dane=" + id_klient);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return new Dane(sqlReturnValues.getInt(1), sqlReturnValues.getString(2),
                        sqlReturnValues.getString(3), sqlReturnValues.getString(4), sqlReturnValues.getString(5));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Pracownik getPracownkInformacje() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM pracownicy WHERE login=" + "'" + sesjaEmail + "'");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return new Pracownik(sqlReturnValues.getInt(1), sqlReturnValues.getInt(2), sqlReturnValues.getString(3),
                        sqlReturnValues.getString(4));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Stanowisko getStanowiskoPracownik(Integer id_stanowisko) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM stanowiska WHERE id_stanowisko=" + id_stanowisko);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return new Stanowisko(sqlReturnValues.getInt(1), sqlReturnValues.getString(2));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean createRezerwacja(Rezerwacja rezerwacja) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO rezerwacje (id_rezerwacje, ksiazki_id_ksiazka,klienci_id_klient,data_2) VALUES (?,?,?,?);");
            insertStatement.setInt(1, getMaxIndexRezerwacje() + 1);
            insertStatement.setInt(2, rezerwacja.getKsiazka_id());
            insertStatement.setInt(3, getKlientInformacje().getId());
            insertStatement.setDate(4, rezerwacja.getDate());
            insertStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean createRezerwacjaAdmin(Rezerwacja rezerwacja) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
//                    "INSERT INTO rezerwacje ( ksiazki_id_ksiazka,klienci_id_klient,data_2) VALUES (?,?,?);");
                    "INSERT INTO rezerwacje (id_rezerwacje, ksiazki_id_ksiazka,klienci_id_klient,data_2) VALUES (?,?,?,?);");
            insertStatement.setInt(1, getMaxIndexRezerwacje() + 1);
            insertStatement.setInt(2, rezerwacja.getKsiazka_id());
            insertStatement.setInt(3, rezerwacja.getKlient_id());
            insertStatement.setDate(4, rezerwacja.getDate());
            insertStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Integer getMaxIndexRezerwacje() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_rezerwacje) FROM rezerwacje ");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMaxIndexKlienci() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_klient) FROM klienci");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMaxIndexStanowiska() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_stanowisko) FROM stanowiska");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMaxIndexAutorzy() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_autor) FROM autorzy");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMaxIndexKsiazki() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_ksiazka) FROM ksiazki");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getKsiazkaId(String tytul) {
        try {
            System.out.println(tytul);
            PreparedStatement stmt = connection.prepareStatement("SELECT id_ksiazka FROM ksiazki WHERE ksiazki.tytul=" + "'" + tytul + "'");
            ResultSet sqlReturnValues = stmt.executeQuery();

            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego tytulu");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getKlientId(String imie, String nazwisko) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT id_klient FROM klienci WHERE imie=" + imie + " AND nazwisko=" + nazwisko);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego tytulu");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMaxIndexDane() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_dane) FROM dane");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Dane> getDane() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dane");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Dane> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Dane(sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2), sqlReturnValues.getString(3)
                        , sqlReturnValues.getString(4), sqlReturnValues.getString(5)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteDane(Integer id_dane) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM dane WHERE id_dane=" + id_dane);
            stmt.executeUpdate();
            System.out.println("Usunieto Dane");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createDane(Dane dane) {
        try {
            PreparedStatement insertDane = connection.prepareStatement(
                    "INSERT INTO dane (id_dane, miasto, ulica, nr_domu, kod_pocztowy) VALUES (?,?,?,?,?)");
            insertDane.setInt(1, getMaxIndexDane() + 1);
            insertDane.setString(2, dane.getMiasto());
            insertDane.setString(3, dane.getUlica());
            insertDane.setString(4, dane.getNr_domu());
            insertDane.setString(5, dane.getKod_pocztowy());


            insertDane.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Wypozyczenie> getWypozyczenia() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM pracownicy_has_klienci");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Wypozyczenie> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Wypozyczenie(sqlReturnValues.getInt(1),
                        sqlReturnValues.getInt(2), sqlReturnValues.getInt(3)
                        , sqlReturnValues.getDate(4), sqlReturnValues.getDate(5)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteWypozyczenie(Integer id_pracownik, Integer id_klient, Integer id_ksiazka) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM pracownicy_has_klienci WHERE" +
                    " pracownicy_id_pracownik=" + id_pracownik + " AND klienci_id_klient=" + id_klient + "" +
                    " AND ksiazki_id_ksiazka=" + id_ksiazka);
            stmt.executeUpdate();
            System.out.println("Usunieto Wypozyczenie");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createWypozyczenie(Wypozyczenie wypozyczenie) {
        try {
            PreparedStatement insertWypozyczenie = connection.prepareStatement(
                    "INSERT INTO pracownicy_has_klienci" +
                            " (pracownicy_id_pracownik, klienci_id_klient, ksiazki_id_ksiazka, data_wypozyczenia, data_oddania) " +
                            "VALUES (?,?,?,?,?)");
            insertWypozyczenie.setInt(1, wypozyczenie.getId_pracownik());
            insertWypozyczenie.setInt(2, wypozyczenie.getId_klient());
            insertWypozyczenie.setInt(3, wypozyczenie.getId_ksiazka());
            insertWypozyczenie.setDate(4, wypozyczenie.getData_wypozyczenia());
            insertWypozyczenie.setDate(5, wypozyczenie.getData_oddania());
            insertWypozyczenie.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<AutorzyKsiazek> getAutorzyKsiazek() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM autorzy_ksiazki");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<AutorzyKsiazek> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new AutorzyKsiazek(sqlReturnValues.getInt(1),
                        sqlReturnValues.getInt(2)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAutorzyKsiazek(Integer id_autor, Integer id_ksiazka) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM autorzy_ksiazki WHERE" +
                    " autorzy_id_autor=" + id_autor + " AND ksiazki_id_ksiazka=" + id_ksiazka);
            stmt.executeUpdate();
            System.out.println("Usunieto AutorzyKsiazek");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createAutorzyKsiazek(AutorzyKsiazek autorzyKsiazek) {
        try {
            PreparedStatement insertAutorzyKsiazek = connection.prepareStatement(
                    "INSERT INTO autorzy_ksiazki (autorzy_id_autor, ksiazki_id_ksiazka) VALUES (?,?);");
            insertAutorzyKsiazek.setInt(1, autorzyKsiazek.getAutor_id());
            insertAutorzyKsiazek.setInt(2, autorzyKsiazek.getKsiazka_id());
            insertAutorzyKsiazek.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Wydawnictwo> getWydawnictwa() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM wydawnictwa");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Wydawnictwo> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Wydawnictwo(sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMaxIndexWydawnictwa() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_wydawnictwa) FROM wydawnictwa");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteWydawnictwo(Integer id_wydawnictwo) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM wydawnictwa WHERE id_wydawnictwa=" + id_wydawnictwo);
            stmt.executeUpdate();
            System.out.println("Usunieto Wydawnictwo");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createWydawnictwo(Wydawnictwo wydawnictwo) {
        try {
            PreparedStatement insertWydawnictwo = connection.prepareStatement(
                    "INSERT INTO wydawnictwa (id_wydawnictwa, nazwa) VALUES (?,?);");
            insertWydawnictwo.setInt(1, getMaxIndexWydawnictwa() + 1);
            insertWydawnictwo.setString(2, wydawnictwo.getNazwa());
            insertWydawnictwo.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Gatunek> getGatunki() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM gatunki");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Gatunek> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Gatunek(sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMaxIndexGatunki() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_gatunek) FROM gatunki");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteGatunek(Integer id_gatunek) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM gatunki WHERE id_gatunek=" + id_gatunek);
            stmt.executeUpdate();
            System.out.println("Usunieto Gatunek");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createGatunek(Gatunek gatunek) {
        try {
            PreparedStatement insertGatunek = connection.prepareStatement(
                    "INSERT INTO gatunki (id_gatunek, nazwa) VALUES (?,?);");
            insertGatunek.setInt(1, getMaxIndexGatunki() + 1);
            insertGatunek.setString(2, gatunek.getNazwa());
            insertGatunek.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Pracownik> getPracownicy() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM pracownicy");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Pracownik> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Pracownik(sqlReturnValues.getInt(1),
                        sqlReturnValues.getInt(2), sqlReturnValues.getString(3),
                        sqlReturnValues.getString(4)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer getMaxIndexPracownicy() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id_pracownik) FROM pracownicy");
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego datebase");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void deletePracownik(Integer id_pracownik) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM pracownicy WHERE id_pracownik=" + id_pracownik);
            stmt.executeUpdate();
            System.out.println("Usunieto Pracownika");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean createPracownik(Pracownik pracownik) {
        try {
            PreparedStatement insertPracownik = connection.prepareStatement(
                    "INSERT INTO pracownicy (id_pracownik, stanowiska_id_stanowisko,login,haslo) VALUES (?,?,?,?);");
            insertPracownik.setInt(1, getMaxIndexPracownicy() + 1);
            insertPracownik.setInt(2, pracownik.getStanowisko_id());
            insertPracownik.setString(3, pracownik.getLogin());
            insertPracownik.setString(4, pracownik.getHaslo());
            insertPracownik.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }
}

