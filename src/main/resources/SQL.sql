-- CREATE TABLE friends (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(200) NOT NULL,
--     phone_number INTEGER NOT NULL
-- );
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

-- CREATE TABLE ksiazki (
--                          id_ksiazka SERIAL PRIMARY KEY   ,
--                          pracownicy_has_klienci_klienci_id_klient INTEGER   NOT NULL  ,
--                          pracownicy_has_klienci_pracownicy_id_pracownik INTEGER   NOT NULL  ,
--                          gatunki_id_gatunek INTEGER   NOT NULL  ,
--                          wydawnictwa_id_wydawnictwa INTEGER   NOT NULL  ,
--                          rezerwacje_id_rezerwacje INTEGER   NOT NULL  ,
--                          tytul VARCHAR  NULL  ,
--                          liczba_stron INTEGER   NULL  ,
--                          opis VARCHAR  NULL   );
-- CREATE TABLE ksiazki (
--                          id_ksiazka  SERIAL PRIMARY KEY,
--                          gatunki_id_gatunek INTEGER   NOT NULL  ,
--                          wydawnictwa_id_wydawnictwa INTEGER   NOT NULL  ,
--                          rezerwacje_id_rezerwacje INTEGER   NOT NULL  ,
--                          tytul VARCHAR  NULL  ,
--                          liczba_stron INTEGER  NULL  ,
--                          opis VARCHAR  NULL );

CREATE TABLE ksiazki
(
    id_ksiazka                 SERIAL PRIMARY KEY,
    gatunki_id_gatunek         INTEGER NOT NULL,
    wydawnictwa_id_wydawnictwa INTEGER NOT NULL,
    tytul                      VARCHAR NULL,
    liczba_stron               INTEGER NULL,
    opis                       VARCHAR NULL
);



-- CREATE TABLE rezerwacje (
--                             id_rezerwacje SERIAL PRIMARY KEY,
--                             klienci_id_klient INTEGER   NOT NULL  ,
--                             data_2 DATE  NULL  ,
--                             id_ksiazka INTEGER   NULL  );

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

-- CREATE TABLE pracownicy_has_klienci (
--                                          pracownicy_id_pracownik INTEGER  NOT NULL  ,
--                                          klienci_id_klient INTEGER  NOT NULL  ,
--                                          data_wyporzyczenia DATE  NULL  ,
--                                          data_oddania DATE  NULL    ,
--                                          PRIMARY KEY(pracownicy_id_pracownik, klienci_id_klient)  );
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

-- ALTER TABLE ksiazki ADD FOREIGN KEY ("rezerwacje_id_rezerwacje") REFERENCES "rezerwacje" ("id_rezerwacje");

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

-- ALTER TABLE ksiazki ADD FOREIGN KEY ("pracownicy_has_klienci_klienci_id_klient") REFERENCES "pracownicy_has_klienci" ("klienci_id_klient");

-- ALTER TABLE ksiazki ADD FOREIGN KEY ("pracownicy_has_klienci_pracownicy_id_pracownik") REFERENCES "pracownicy_has_klienci" ("pracownicy_id_pracownik");


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

INSERT INTO pracownicy_has_klienci (pracownicy_id_pracownik, klienci_id_klient, ksiazki_id_ksiazka, data_wyporzyczenia,
                                    data_oddania)
VALUES (1, 1, 1, '2021-12-21', '2021-12-31');

INSERT INTO pracownicy_has_klienci (pracownicy_id_pracownik, klienci_id_klient, ksiazki_id_ksiazka, data_wyporzyczenia,
                                    data_oddania)
VALUES (1, 2, 1, '2021-12-26', '2022-01-15');


-- CREATE OR REPLACE FUNCTION usuwanieRezerwacjiKlienta (id_k int) RETURNS text AS $$
--
-- BEGIN
--     DELETE FROM klienci WHERE id_klient = id_k;
--     IF NOT FOUND THEN
--         RAISE EXCEPTION 'Klienta % nie ma w bazie', id_k;
--     END IF;
--     RETURN 'Success';
-- END;
-- $$
--     LANGUAGE 'plpgsql';
--
-- CREATE TRIGGER usuwanieRezerwacji BEFORE DELETE ON klienci
--     FOR EACH ROW
-- EXECUTE PROCEDURE usuwanieRezerwacjiKlienta();