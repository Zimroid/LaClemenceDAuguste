-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Ven 07 Mars 2014 à 16:46
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `augusteweb`
--
CREATE DATABASE IF NOT EXISTS `augusteweb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `augusteweb`;

-- --------------------------------------------------------

--
-- Structure de la table `faq`
--

CREATE TABLE IF NOT EXISTS `faq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(150) NOT NULL,
  `reponse` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `faq`
--

INSERT INTO `faq` (`id`, `question`, `reponse`) VALUES
(1, 'alpha', 'réponse a'),
(2, 'beta', 'réponse b');

-- --------------------------------------------------------

--
-- Structure de la table `news`
--

CREATE TABLE IF NOT EXISTS `news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `image` varchar(150) NOT NULL,
  `contenu` varchar(1500) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
--
-- Base de données: `lpart`
--
CREATE DATABASE IF NOT EXISTS `lpart` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `lpart`;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE IF NOT EXISTS `categorie` (
  `idCategorie` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `labelCategorie` varchar(50) NOT NULL,
  PRIMARY KEY (`idCategorie`),
  KEY `idUser` (`idUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Contenu de la table `categorie`
--

INSERT INTO `categorie` (`idCategorie`, `idUser`, `labelCategorie`) VALUES
(16, 88, 'Categ 1'),
(18, 91, 'cat 1');

-- --------------------------------------------------------

--
-- Structure de la table `image`
--

CREATE TABLE IF NOT EXISTS `image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `url` varchar(300) NOT NULL,
  `auteur` int(11) DEFAULT NULL,
  `dateAjout` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateDernVue` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `purityGrade` int(2) NOT NULL DEFAULT '0' COMMENT '0, 1, 2',
  PRIMARY KEY (`id`),
  KEY `auteur` (`auteur`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=52 ;

--
-- Contenu de la table `image`
--

INSERT INTO `image` (`id`, `nom`, `url`, `auteur`, `dateAjout`, `dateDernVue`, `purityGrade`) VALUES
(44, 'Cat', 'Cat.jpg', 88, '2014-01-31 09:22:58', '2014-01-31 12:20:33', 0),
(45, 'Nature', 'Nature.jpg', 88, '2014-01-31 09:52:56', '2014-01-31 12:52:06', 0),
(46, 'The ring', 'The ring.jpg', 88, '2014-01-31 10:00:16', '2014-01-31 10:39:06', 0),
(47, 'Paris', 'Paris.jpg', 89, '2014-01-31 10:00:37', '2014-01-31 10:50:07', 0),
(48, 'Cabin', 'Cabin.jpg', 89, '2014-01-31 10:01:18', '2014-01-31 10:40:27', 0),
(49, 'Mountains', 'Mountains.jpg', 89, '2014-01-31 10:01:26', '2014-01-31 12:57:52', 0),
(50, 'Hexagone', 'Hexagon.jpg', 88, '2014-01-31 10:51:53', '2014-01-31 12:57:46', 0),
(51, 'Mountains', 'Mountains.jpg', 91, '2014-01-31 12:51:04', '2014-01-31 12:54:00', 0);

-- --------------------------------------------------------

--
-- Structure de la table `note`
--

CREATE TABLE IF NOT EXISTS `note` (
  `idUser` int(11) NOT NULL DEFAULT '0',
  `idImage` int(11) NOT NULL,
  `note` int(11) NOT NULL,
  PRIMARY KEY (`idUser`,`idImage`),
  KEY `idImage` (`idImage`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `note`
--

INSERT INTO `note` (`idUser`, `idImage`, `note`) VALUES
(88, 44, 6),
(88, 45, 10),
(88, 46, 9),
(88, 51, 9),
(89, 46, 6),
(89, 47, 3),
(91, 51, 8);

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

CREATE TABLE IF NOT EXISTS `panier` (
  `idCategorie` int(11) NOT NULL,
  `idImage` int(11) NOT NULL,
  PRIMARY KEY (`idCategorie`,`idImage`),
  KEY `ImageEnregistrer` (`idImage`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `panier`
--

INSERT INTO `panier` (`idCategorie`, `idImage`) VALUES
(16, 50),
(16, 51);

-- --------------------------------------------------------

--
-- Structure de la table `tag`
--

CREATE TABLE IF NOT EXISTS `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `recherche` varchar(30) NOT NULL,
  `label` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Contenu de la table `tag`
--

INSERT INTO `tag` (`id`, `recherche`, `label`) VALUES
(4, 'CHAT', 'Chat'),
(5, 'FEU', 'Feu'),
(7, 'ARBRE', 'Arbre'),
(8, 'LUMIERE', 'Lumiere'),
(9, 'PAYSAGE', 'Paysage'),
(10, 'RING', 'Ring'),
(11, 'ANNEAU', 'Anneau'),
(12, 'HEXAGONE', 'Hexagone'),
(13, 'TOUR', 'Tour'),
(14, 'VILLE', 'Ville'),
(15, '123', '123');

-- --------------------------------------------------------

--
-- Structure de la table `tagimage`
--

CREATE TABLE IF NOT EXISTS `tagimage` (
  `idImage` int(11) NOT NULL,
  `idTag` int(11) NOT NULL,
  PRIMARY KEY (`idImage`,`idTag`),
  KEY `ImageTag` (`idTag`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `tagimage`
--

INSERT INTO `tagimage` (`idImage`, `idTag`) VALUES
(44, 4),
(45, 7),
(45, 8),
(45, 9),
(47, 9),
(48, 9),
(49, 9),
(46, 10),
(46, 11),
(50, 12),
(47, 14),
(51, 15);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=92 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id`, `login`, `pwd`) VALUES
(88, 'test', 'a94a8fe5ccb19ba61c4c0873d391e987982fbbd3'),
(89, 'test2', '109f4b3c50d7b0df729d299bc6f8e9ef9066971f'),
(90, '1', '356a192b7913b04c54574d18c28d46e6395428ab'),
(91, 'b', 'e9d71f5ee7c92d6dc9e92ffdad17b8bd49418f98');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD CONSTRAINT `ProprietaireCategorie` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `Auteur` FOREIGN KEY (`auteur`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `note`
--
ALTER TABLE `note`
  ADD CONSTRAINT `note_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `note_ibfk_2` FOREIGN KEY (`idImage`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `panier`
--
ALTER TABLE `panier`
  ADD CONSTRAINT `CategorieStockage` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ImageEnregistrer` FOREIGN KEY (`idImage`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `tagimage`
--
ALTER TABLE `tagimage`
  ADD CONSTRAINT `ImageTag` FOREIGN KEY (`idTag`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `TagImage` FOREIGN KEY (`idImage`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Base de données: `panda`
--
CREATE DATABASE IF NOT EXISTS `panda` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `panda`;
--
-- Base de données: `pieces`
--
CREATE DATABASE IF NOT EXISTS `pieces` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `pieces`;

-- --------------------------------------------------------

--
-- Structure de la table `composition`
--

CREATE TABLE IF NOT EXISTS `composition` (
  `Piece Composee` int(11) NOT NULL,
  `Composante` int(11) NOT NULL,
  PRIMARY KEY (`Piece Composee`,`Composante`),
  KEY `Compose` (`Composante`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `composition`
--

INSERT INTO `composition` (`Piece Composee`, `Composante`) VALUES
(3, 1),
(3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `pieces`
--

CREATE TABLE IF NOT EXISTS `pieces` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Nom` varchar(100) NOT NULL,
  `EstComposee` tinyint(1) NOT NULL,
  `Stock` int(11) NOT NULL,
  `Prix` decimal(10,0) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `pieces`
--

INSERT INTO `pieces` (`Id`, `Nom`, `EstComposee`, `Stock`, `Prix`) VALUES
(1, 'Stick', 0, 10, '10'),
(2, 'Diamond', 0, 20, '50'),
(3, 'Diamond Sword', 1, 5, '150');

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `composition`
--
ALTER TABLE `composition`
  ADD CONSTRAINT `Compose` FOREIGN KEY (`Composante`) REFERENCES `pieces` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Est composee` FOREIGN KEY (`Piece Composee`) REFERENCES `pieces` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Base de données: `test`
--
CREATE DATABASE IF NOT EXISTS `test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `test`;

-- --------------------------------------------------------

--
-- Structure de la table `events`
--

CREATE TABLE IF NOT EXISTS `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `datetime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
