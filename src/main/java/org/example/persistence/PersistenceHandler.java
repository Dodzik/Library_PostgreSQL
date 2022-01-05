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
            stmt.setString(1, email);
            stmt.setString(2, haslo);
            ResultSet sqlReturnValues = stmt.executeQuery();
            System.out.println("email: " + email + " haslo: " + haslo);

            if (!sqlReturnValues.next()) {
                System.out.println("nie udalo sie");
                return false;
            } else {
                sesjaEmail = email;
                System.out.println("udalo sie");
                return true;
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public List<Friend> getFriends() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM friends");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Friend> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Friend(sqlReturnValues.getInt(1), sqlReturnValues.getString(2), sqlReturnValues.getInt(3)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

//    @Override
//    public List<Klient> getKlienci() {
//        try {
//            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM klienci");
//            ResultSet sqlReturnValues = stmt.executeQuery();
//            List<Klient> returnValues = new ArrayList<>();
//
//            while (sqlReturnValues.next()) {
//                returnValues.add(new Klient(sqlReturnValues.getInt(1), sqlReturnValues.getString(2),
//                        sqlReturnValues.getString(3), sqlReturnValues.getString(4), sqlReturnValues.getString(5)));
//            }
//            return returnValues;
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
//        return null;
//    }

    @Override
    public List<Book> getKsiazki() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ksiazki");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Book> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Book(sqlReturnValues.getInt(1), sqlReturnValues.getInt(2),
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
    public List<Book> getKsiazkiAutora() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM autorzy_ksiazki WHERE autorzy_id_autor=" + "'" + temp + "'");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Book> returnValues = new ArrayList<>();

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
    public Book getKsiazkaById(Integer id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ksiazki WHERE id_ksiazka=" + id);
            ResultSet sqlReturnValues = stmt.executeQuery();

            while (sqlReturnValues.next()) {
                return new Book(sqlReturnValues.getInt(1), sqlReturnValues.getInt(2),
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
    public String getNazwaKsiazka(Integer id_ksiazka) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT ksiazki.tytul FROM ksiazki WHERE ksiazki.id_ksiazka=" + id_ksiazka);
            ResultSet sqlReturnValues = stmt.executeQuery();

            while (sqlReturnValues.next()) {
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

            while (sqlReturnValues.next()) {
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
//            String imie ="";
//            String nazwisko="";
            while (sqlReturnValues.next()) {
                Klient_id = sqlReturnValues.getInt(1);
//                imie=sqlReturnValues.getString(3);
//                nazwisko=sqlReturnValues.getString(4);
            }

            PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM rezerwacje WHERE rezerwacje.klienci_id_klient=" + Klient_id);
            ResultSet sqlReturnValues2 = stmt2.executeQuery();

            List<Rezerwacja> returnValues = new ArrayList<>();
            while (sqlReturnValues2.next()) {
//                System.out.println(new Rezerwacja(sqlReturnValues2.getInt(1),sqlReturnValues2.getInt(2),
//                        sqlReturnValues2.getInt(3),
//                        sqlReturnValues2.getDate(4)));
                returnValues.add(new Rezerwacja(sqlReturnValues2.getInt(1), sqlReturnValues2.getInt(2),
                        sqlReturnValues2.getInt(3),
                        sqlReturnValues2.getDate(4)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public String getGatunekId(Integer gatunek_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT gatunki.nazwa FROM gatunki WHERE gatunki.id_gatunek=" + gatunek_id);
            ResultSet sqlReturnValues = stmt.executeQuery();

            while (sqlReturnValues.next()) {
                return sqlReturnValues.getString(1);
            }
            return "nie ma takiego gatunku";
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public String getWydawnictwoById(Integer wydownictwo_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT wydawnictwa.nazwa FROM wydawnictwa WHERE wydawnictwa.id_wydawnictwa=" + wydownictwo_id);
            ResultSet sqlReturnValues = stmt.executeQuery();

            while (sqlReturnValues.next()) {
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
            while (sqlReturnValues.next()) {
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
            while (sqlReturnValues.next()) {
                return new Dane(sqlReturnValues.getInt(1), sqlReturnValues.getString(2),
                        sqlReturnValues.getString(3), sqlReturnValues.getString(4), sqlReturnValues.getString(5));
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
    public boolean createFriend(Friend friend) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO friends (name, phone_number) VALUES (?,?);");
            insertStatement.setString(1, friend.getName());
            insertStatement.setInt(2, friend.getPhone());
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
            while (sqlReturnValues.next()) {
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

            while (sqlReturnValues.next()) {
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
            while (sqlReturnValues.next()) {
                return sqlReturnValues.getInt(1);
            }
            System.out.println("nie ma takiego tytulu");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
