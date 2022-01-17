CREATE TABLE autorzy
(
    id_autor SERIAL PRIMARY KEY,
    imie     VARCHAR NULL,
    nazwisko VARCHAR NULL
);

CREATE TABLE autorzy_ksiazki
(
    autorzy_id_autor   INTEGER NOT NULL,
    ksiazki_id_ksiazka INTEGER NOT NULL,
    PRIMARY KEY (autorzy_id_autor, ksiazki_id_ksiazka)
);

CREATE TABLE gatunki
(
    id_gatunek SERIAL PRIMARY KEY,
    nazwa      VARCHAR NULL
);

CREATE TABLE wydawnictwa
(
    id_wydawnictwa SERIAL PRIMARY KEY,
    nazwa          VARCHAR NULL
);

CREATE TABLE ksiazki
(
    id_ksiazka                 SERIAL PRIMARY KEY,
    gatunki_id_gatunek         INTEGER NOT NULL,
    wydawnictwa_id_wydawnictwa INTEGER NOT NULL,
    tytul                      VARCHAR NULL,
    liczba_stron               INTEGER NULL,
    opis                       VARCHAR NULL
);


CREATE TABLE rezerwacje
(
    id_rezerwacje      SERIAL PRIMARY KEY,
    ksiazki_id_ksiazka INTEGER NOT NULL,
    klienci_id_klient  INTEGER NOT NULL,
    data_2             DATE NULL
);



CREATE TABLE klienci
(
    id_klient    SERIAL PRIMARY KEY,
    dane_id_dane INTEGER NOT NULL,
    imie         VARCHAR NULL,
    nazwisko     VARCHAR NULL,
    email        VARCHAR NULL,
    haslo        VARCHAR NULL
);

CREATE TABLE stanowiska
(
    id_stanowisko SERIAL PRIMARY KEY,
    nazwa         VARCHAR NULL
);

CREATE TABLE pracownicy
(
    id_pracownik             SERIAL PRIMARY KEY,
    stanowiska_id_stanowisko INTEGER NOT NULL,
    login                    VARCHAR NULL,
    haslo                    VARCHAR NULL
);

CREATE TABLE pracownicy_has_klienci
(
    pracownicy_id_pracownik INTEGER NOT NULL,
    klienci_id_klient       INTEGER NOT NULL,
    ksiazki_id_ksiazka      INTEGER NOT NULL,
    data_wyporzyczenia      DATE NULL,
    data_oddania            DATE NULL,
    PRIMARY KEY (pracownicy_id_pracownik, klienci_id_klient)
);


CREATE TABLE dane
(
    id_dane      SERIAL PRIMARY KEY,
    miasto       VARCHAR NULL,
    ulica        VARCHAR NULL,
    nr_domu      VARCHAR NULL,
    kod_pocztowy VARCHAR NULL
);


ALTER TABLE autorzy_ksiazki
    ADD FOREIGN KEY ("autorzy_id_autor") REFERENCES "autorzy" ("id_autor");

ALTER TABLE autorzy_ksiazki
    ADD FOREIGN KEY ("ksiazki_id_ksiazka") REFERENCES "ksiazki" ("id_ksiazka");

ALTER TABLE ksiazki
    ADD FOREIGN KEY ("gatunki_id_gatunek") REFERENCES "gatunki" ("id_gatunek");

ALTER TABLE ksiazki
    ADD FOREIGN KEY ("wydawnictwa_id_wydawnictwa") REFERENCES "wydawnictwa" ("id_wydawnictwa");

ALTER TABLE pracownicy
    ADD FOREIGN KEY ("stanowiska_id_stanowisko") REFERENCES "stanowiska" ("id_stanowisko");

ALTER TABLE pracownicy_has_klienci
    ADD FOREIGN KEY ("pracownicy_id_pracownik") REFERENCES "pracownicy" ("id_pracownik");

ALTER TABLE pracownicy_has_klienci
    ADD FOREIGN KEY ("klienci_id_klient") REFERENCES "klienci" ("id_klient");

ALTER TABLE pracownicy_has_klienci
    ADD FOREIGN KEY ("ksiazki_id_ksiazka") REFERENCES "ksiazki" ("id_ksiazka");

ALTER TABLE rezerwacje
    ADD FOREIGN KEY ("klienci_id_klient") REFERENCES "klienci" ("id_klient");

ALTER TABLE rezerwacje
    ADD FOREIGN KEY ("ksiazki_id_ksiazka") REFERENCES "ksiazki" ("id_ksiazka");

ALTER TABLE klienci
    ADD FOREIGN KEY ("dane_id_dane") REFERENCES "dane" ("id_dane");

INSERT INTO autorzy (id_autor, imie, nazwisko)
VALUES (1, 'Mateusz', 'Kowalski');

INSERT INTO autorzy (id_autor, imie, nazwisko)
VALUES (2, 'Piotr', 'Adamczyk');

INSERT INTO gatunki (id_gatunek, nazwa)
VALUES (1, 'Kryminal');

INSERT INTO gatunki (id_gatunek, nazwa)
VALUES (2, 'Bajka');

INSERT INTO gatunki (id_gatunek, nazwa)
VALUES (3, 'Powiesc');

INSERT INTO wydawnictwa (id_wydawnictwa, nazwa)
VALUES (1, 'Potockie');

INSERT INTO wydawnictwa (id_wydawnictwa, nazwa)
VALUES (2, 'Literka');

INSERT INTO stanowiska (id_stanowisko, nazwa)
VALUES (1, 'Ksiegowy');

INSERT INTO stanowiska (id_stanowisko, nazwa)
VALUES (2, 'Sprzatacz');

INSERT INTO dane (id_dane, miasto, ulica, nr_domu, kod_pocztowy)
VALUES (1, 'Lublin', 'Zielona', '2', '22-304');

INSERT INTO dane (id_dane, miasto, ulica, nr_domu, kod_pocztowy)
VALUES (2, 'Krakow', 'Czerwona', '34', '30-124');

INSERT INTO klienci (id_klient, dane_id_dane, imie, nazwisko, email, haslo)
VALUES (1, 1, 'Mariusz', 'Grosik', 'Grosik@email.com', 'test');

INSERT INTO klienci (id_klient, dane_id_dane, imie, nazwisko, email, haslo)
VALUES (2, 2, 'Marcin', 'Zlotowka', 'Zlotowka@email.com', 'test');

INSERT INTO klienci (id_klient, dane_id_dane, imie, nazwisko, email, haslo)
VALUES (3, 2, 'Kamil', 'Kowal', 'Kowal@email.com', 'test');

INSERT INTO ksiazki (id_ksiazka, gatunki_id_gatunek, wydawnictwa_id_wydawnictwa, tytul, liczba_stron, opis)
VALUES (1, 1, 1, 'Zabili go i uciakl', 89, 'Ksiazka oparta na histori czlowieka ktory zostal zabity i uciekl');

INSERT INTO ksiazki (id_ksiazka, gatunki_id_gatunek, wydawnictwa_id_wydawnictwa, tytul, liczba_stron, opis)
VALUES (2, 3, 2, 'Pajacyki', 34, 'Powiesc o pajacykach');

INSERT INTO autorzy_ksiazki (autorzy_id_autor, ksiazki_id_ksiazka)
VALUES (1, 2);

INSERT INTO autorzy_ksiazki (autorzy_id_autor, ksiazki_id_ksiazka)
VALUES (2, 2);

INSERT INTO autorzy_ksiazki (autorzy_id_autor, ksiazki_id_ksiazka)
VALUES (1, 1);

INSERT INTO rezerwacje (id_rezerwacje, ksiazki_id_ksiazka, klienci_id_klient, data_2)
VALUES (1, 1, 1, '2021-12-21');

INSERT INTO rezerwacje (id_rezerwacje, ksiazki_id_ksiazka, klienci_id_klient, data_2)
VALUES (2, 2, 1, '2021-12-26');

INSERT INTO rezerwacje (id_rezerwacje, ksiazki_id_ksiazka, klienci_id_klient, data_2)
VALUES (3, 1, 3, '2021-12-16');

INSERT INTO pracownicy (id_pracownik, stanowiska_id_stanowisko, login, haslo)
VALUES (1, 1, 'admin', 'admin');

INSERT INTO pracownicy (id_pracownik, stanowiska_id_stanowisko, login, haslo)
VALUES (2, 1, 'admin2', 'admin2');

INSERT INTO pracownicy_has_klienci (pracownicy_id_pracownik, klienci_id_klient, ksiazki_id_ksiazka, data_wypozyczenia,
                                    data_oddania)
VALUES (1, 1, 1, '2021-12-21', '2021-12-31');

INSERT INTO pracownicy_has_klienci (pracownicy_id_pracownik, klienci_id_klient, ksiazki_id_ksiazka, data_wypozyczenia,
                                    data_oddania)
VALUES (1, 2, 1, '2021-12-26', '2022-01-15');


-----------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION zmien_haslo_pracownik(idPrac int, oldpass varchar, newpass varchar)
    RETURNS integer AS
$$
DECLARE

BEGIN
    IF EXISTS(SELECT 1 FROM pracownicy p WHERE p.id_pracownik=idPrac and p.haslo=oldpass) THEN
        update pracownicy
        set haslo = newpass
        where id_pracownik=idPrac;
        return 1;
    else
        return 0;
    end if;
END
$$
    LANGUAGE plpgsql;
-----------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION zmien_haslo_klient(idKlient int, oldpass varchar, newpass varchar)
    RETURNS integer AS
$$
DECLARE

BEGIN
    IF EXISTS(SELECT 1 FROM klienci p WHERE p.id_klient=idKlient and p.haslo=oldpass) THEN
        update klienci
        set haslo = newpass
        where id_klient=idKlient;
        return 1;
    else
        return 0;
    end if;
END
$$
    LANGUAGE plpgsql;
-----------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION update_ksiazka_tytul(idKsiazka int , nowyTytul varchar )
    RETURNS integer AS
$$
DECLARE

BEGIN
    IF EXISTS(SELECT 1 FROM ksiazki p WHERE p.id_ksiazka=idKsiazka) THEN
        update ksiazki
        set tytul = nowyTytul
        where id_ksiazka=idKsiazka;
        return 1;
    else
        return 0;
    end if;
END
$$
    LANGUAGE plpgsql;
-----------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION insert_pracownik() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM pracownicy p WHERE p.login = New.login ) THEN

        RAISE EXCEPTION 'Taki pracownik już istnieje!';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER pracownicy_insert_validation
    BEFORE INSERT OR UPDATE ON pracownicy
    FOR EACH ROW EXECUTE PROCEDURE insert_pracownik();

------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION insert_klient() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM klienci p WHERE p.email = New.email ) THEN

        RAISE EXCEPTION 'Taki klient już istnieje!';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER klient_insert_validation
    BEFORE INSERT OR UPDATE ON klienci
    FOR EACH ROW EXECUTE PROCEDURE insert_klient();
------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION insert_ksiazka() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM ksiazki p WHERE p.tytul = New.tytul ) THEN

        RAISE EXCEPTION 'Taka ksiazka już istnieje!';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER ksiazka_insert_validation
    BEFORE INSERT OR UPDATE ON ksiazki
    FOR EACH ROW EXECUTE PROCEDURE insert_ksiazka();
------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION insert_wydawnictwo() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM wydawnictwa p WHERE p.nazwa = New.nazwa ) THEN

        RAISE EXCEPTION 'Taka wydawnictwo już istnieje!';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER wydawnictwo_insert_validation
    BEFORE INSERT OR UPDATE ON wydawnictwa
    FOR EACH ROW EXECUTE PROCEDURE insert_wydawnictwo();
------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION insert_autor_ksiazki() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM autorzy_ksiazki p
    WHERE p.autorzy_id_autor = New.autorzy_id_autor AND p.ksiazki_id_ksiazka = New.ksiazki_id_ksiazka  ) THEN

        RAISE EXCEPTION 'Taka relacja już istnieje!';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER autorzy_ksiazki_insert_validation
    BEFORE INSERT OR UPDATE ON autorzy_ksiazki
    FOR EACH ROW EXECUTE PROCEDURE insert_autor_ksiazki();
------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION insert_autor() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM autorzy p WHERE p.imie = New.imie AND p.nazwisko = New.nazwisko) THEN

        RAISE EXCEPTION 'Taki autor już istnieje!';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER autor_insert_validation
    BEFORE INSERT OR UPDATE ON autorzy
    FOR EACH ROW EXECUTE PROCEDURE insert_autor();

------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION insert_gatunek() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM gatunki p WHERE p.nazwa = New.nazwa) THEN

        RAISE EXCEPTION 'Taki gatunek już istnieje!';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER gatunek_insert_validation
    BEFORE INSERT OR UPDATE ON gatunki
    FOR EACH ROW EXECUTE PROCEDURE insert_gatunek();

------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION insert_stanowisko() RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS(SELECT 1 FROM stanowiska p WHERE p.nazwa = New.nazwa) THEN

        RAISE EXCEPTION 'Taki stanowisko już istnieje!';
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE 'plpgsql';

CREATE TRIGGER stanowisko_insert_validation
    BEFORE INSERT OR UPDATE ON stanowiska
    FOR EACH ROW EXECUTE PROCEDURE insert_stanowisko();
------------------------------------------------------------------------------------------------------------

CREATE VIEW lista_pracownikow
as
select p.id_pracownik, p.login, p.haslo, s.id_stanowisko, s.nazwa
from pracownicy p join stanowiska s on p.stanowiska_id_stanowisko = s.id_stanowisko;
------------------------------------------------------------------------------------------------------------

CREATE VIEW  lista_klienci
as
select p.imie,p.nazwisko, p.haslo, p.email,d.miasto, d.ulica, d.nr_domu, d.kod_pocztowy
from klienci p join dane d on p.dane_id_dane = d.id_dane;
------------------------------------------------------------------------------------------------------------

CREATE VIEW  lista_ksiazek
as
SELECT k.id_ksiazka,k.tytul, k.liczba_stron,k.opis,g.nazwa AS gatunek,w.nazwa AS wydawnictwo
from ksiazki k join gatunki g on k.gatunki_id_gatunek = g.id_gatunek
               join wydawnictwa w on w.id_wydawnictwa = k.wydawnictwa_id_wydawnictwa;
------------------------------------------------------------------------------------------------------------
