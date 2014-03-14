-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 14, 2014 at 02:35 PM
-- Server version: 5.5.35
-- PHP Version: 5.3.10-1ubuntu3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `Auguste`
--

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE IF NOT EXISTS `player` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`id`, `login`, `password`) VALUES
(3, 'Lan', '9d4e1e23bd5b727046a9e3b4b7db57bd8d6ee684'),
(7, 'Patate', '14b10468a32dbd4d2be8c996930948818cb1ebdb'),
(8, 'Cadillac', 'a16358be6e2306b153b1f071477e68837266075e'),
(15, 'test', 'e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98'),
(17, 'V', '7a38d8cbd20d9932ba948efaa364bb62651d5ad4'),
(18, '<a href=''flolefries.com''>Flo</a>', '403926033d001b5279df37cbbe5287b7c7c267fa'),
(19, '<a href=''http:\\/\\/www.flolefries.com''>Flo</a>', '403926033d001b5279df37cbbe5287b7c7c267fa'),
(20, 'Compte1', 'f0578f1e7174b1a41c4ea8c6e17f7a8a3b88c92a'),
(21, 'Compte2', '8be52126a6fde450a7162a3651d589bb51e9579d'),
(23, 'vr4el', 'be76331b95dfc399cd776d2fc68021e0db03cc4f'),
(24, 'Hellix', 'b9b3c93a7e1c61ac1e318b658a11d75e353a5559'),
(25, 'divoux', '28a1dde0c751b20c0f71c42be0b3f6d8be1b5e8f');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
