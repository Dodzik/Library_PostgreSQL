package org.example.persistence;

import javafx.scene.paint.Color;
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
    //    private String url = "abul.db.elephantsql.com";
//    private int port = 5432;
//    private String databaseName = "zwvjtqus";
//    private String username = "zwvjtqus";
//    private String password = "2TxSSPNIOQ_1Lml3bELGFn4ANpmFIOtp";
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

    private String sesjaEmail="";

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
                sesjaEmail=email;
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
                returnValues.add(new Book(sqlReturnValues.getInt(1), getGatunek(sqlReturnValues.getInt(2)),
                        getWydawnictwo(sqlReturnValues.getInt(3)), sqlReturnValues.getString(4), sqlReturnValues.getInt(5),
                        sqlReturnValues.getString(6)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public String getNazwaKsiazka(Integer id_ksiazka){
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT ksiazki.tytul FROM ksiazki WHERE ksiazki.id_ksiazka="+id_ksiazka);
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
    public List<Rezerwacja> getRezerwacjeKlient() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM klienci WHERE klienci.email="+"'"+ sesjaEmail+"'");
            ResultSet sqlReturnValues = stmt.executeQuery();
            Integer Klient_id=0;
            String imie ="";
            String nazwisko="";
            while (sqlReturnValues.next()) {
                Klient_id = sqlReturnValues.getInt(1);
                imie=sqlReturnValues.getString(3);
                nazwisko=sqlReturnValues.getString(4);
            }

            PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM rezerwacje WHERE rezerwacje.klienci_id_klient="+Klient_id);
            ResultSet sqlReturnValues2 = stmt2.executeQuery();

            List<Rezerwacja> returnValues = new ArrayList<>();
            while (sqlReturnValues2.next()) {

                returnValues.add(new Rezerwacja(sqlReturnValues2.getInt(1),getNazwaKsiazka(sqlReturnValues2.getInt(1)) ,
                        imie,
                        nazwisko,
                        sqlReturnValues2.getDate(4)));
            }
            return returnValues;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public String getGatunek(Integer gatunek_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT gatunki.nazwa FROM gatunki WHERE gatunki.id_gatunek="+gatunek_id);
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
    public String getWydawnictwo(Integer wydownictwo_id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT wydawnictwa.nazwa FROM wydawnictwa WHERE wydawnictwa.id_wydawnictwa="+wydownictwo_id);
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
    public Klient getKlientInformacje(){
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM klienci WHERE klienci.email="+"'"+ sesjaEmail+"'");
            ResultSet sqlReturnValues = stmt.executeQuery();
            while (sqlReturnValues.next()) {
                return new Klient(sqlReturnValues.getInt(1),sqlReturnValues.getInt(2), sqlReturnValues.getString(3),
                        sqlReturnValues.getString(4), sqlReturnValues.getString(5), sqlReturnValues.getString(6));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public Dane getKlientDane(Integer id_klient){
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM dane WHERE dane.id_dane="+id_klient);
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
}
