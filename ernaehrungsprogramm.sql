-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 24. Apr 2024 um 15:30
-- Server-Version: 10.1.38-MariaDB
-- PHP-Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `ernaehrungsprogramm`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `t_benutzer`
--

CREATE TABLE `t_benutzer` (
  `id` int(10) UNSIGNED NOT NULL,
  `vorname` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `nachname` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `geschlecht` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `ziel` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `groesse` int(3) UNSIGNED NOT NULL,
  `gewicht` int(3) UNSIGNED NOT NULL,
  `geburtsdatum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `t_benutzer`
--

INSERT INTO `t_benutzer` (`id`, `vorname`, `nachname`, `geschlecht`, `ziel`, `groesse`, `gewicht`, `geburtsdatum`) VALUES
(1, 'Max', 'Mustermann', 'Männlich', 'abnehmen', 173, 60, '2002-04-12');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `t_produkte`
--

CREATE TABLE `t_produkte` (
  `id` int(10) UNSIGNED NOT NULL,
  `produktname` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `kategorie` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `kohlenhydrate_menge` int(4) UNSIGNED NOT NULL,
  `eiweiss_menge` int(4) UNSIGNED NOT NULL,
  `fett_menge` int(4) UNSIGNED NOT NULL,
  `kcal_anzahl` int(5) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `t_produkte`
--

INSERT INTO `t_produkte` (`id`, `produktname`, `kategorie`, `kohlenhydrate_menge`, `eiweiss_menge`, `fett_menge`, `kcal_anzahl`) VALUES
(1, 'cola', 'Kohlenhydrate', 90, 0, 10, 900),
(2, 'milch', 'Kohlenhydrate', 50, 40, 10, 600),
(3, 'hähnchenbrust', 'Eiweiss', 0, 100, 0, 400),
(4, 'holz', 'Fett', 50, 30, 20, 514),
(5, 'Zahnpasta', 'Kohlenhydrate', 50, 40, 150, 2300),
(6, 'banane', 'Eiweiss', 90, 5, 5, 400),
(7, 'fernsehen', 'Kohlenhydrate', 30, 30, 30, 20),
(13, 'puppe', 'Kohlenhydrate', 123, 43, 123, 1824),
(14, 'hallo', 'Fett', 12, 23, 44, 552),
(16, 'schnitzel', 'Fett', 48, 33, 98, 1243),
(17, 'käse', 'Eiweiss', 50, 30, 20, 514),
(18, 'Mais', 'Fett', 40, 30, 30, 566),
(19, 'Capri Sonne', 'Kohlenhydrate', 50, 20, 20, 473),
(20, 'Multivitaminsaft', 'Kohlenhydrate', 40, 20, 20, 500),
(21, 'Nudel', 'Kohlenhydrate', 80, 15, 0, 500);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `t_rueckblick`
--

CREATE TABLE `t_rueckblick` (
  `id` int(10) UNSIGNED NOT NULL,
  `benutzer_id` int(10) UNSIGNED NOT NULL,
  `gesamt_kohlenhydrate` int(4) UNSIGNED NOT NULL,
  `gesamt_eiweiss` int(4) UNSIGNED NOT NULL,
  `gesamt_fett` int(4) UNSIGNED NOT NULL,
  `gesamt_kcal` int(5) UNSIGNED NOT NULL,
  `max_kcal` int(5) UNSIGNED NOT NULL,
  `datum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `t_rueckblick`
--

INSERT INTO `t_rueckblick` (`id`, `benutzer_id`, `gesamt_kohlenhydrate`, `gesamt_eiweiss`, `gesamt_fett`, `gesamt_kcal`, `max_kcal`, `datum`) VALUES
(1, 1, 200, 145, 30, 2500, 2200, '2020-05-25'),
(6, 1, 29, 23, 321, 3232, 3232, '2020-05-26'),
(7, 1, 736, 362, 888, 2777, 2888, '2020-05-27'),
(8, 1, 123, 321, 213, 312, 312, '2020-05-28'),
(13, 1, 140, 140, 20, 1900, 2639, '2020-06-17'),
(20, 1, 683, 458, 378, 6644, 1931, '2020-06-20'),
(21, 1, 234, 44, 45, 5435, 4234, '2020-05-04'),
(22, 1, 232, 111, 222, 333, 4444, '2020-05-12'),
(25, 1, 180, 230, 230, 2404, 1931, '2020-06-21'),
(26, 1, 90, 105, 5, 800, 1931, '2020-06-22'),
(27, 1, 100, 86, 172, 2361, 1931, '2020-06-24'),
(28, 1, 150, 116, 192, 2875, 1931, '2020-10-03');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `t_benutzer`
--
ALTER TABLE `t_benutzer`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `t_produkte`
--
ALTER TABLE `t_produkte`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `t_rueckblick`
--
ALTER TABLE `t_rueckblick`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_benutzer_rueckblick` (`benutzer_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `t_benutzer`
--
ALTER TABLE `t_benutzer`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT für Tabelle `t_produkte`
--
ALTER TABLE `t_produkte`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT für Tabelle `t_rueckblick`
--
ALTER TABLE `t_rueckblick`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `t_rueckblick`
--
ALTER TABLE `t_rueckblick`
  ADD CONSTRAINT `fk_benutzer_rueckblick` FOREIGN KEY (`benutzer_id`) REFERENCES `t_benutzer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
