-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 12-03-2014 a las 14:05:47
-- Versión del servidor: 5.1.44
-- Versión de PHP: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `etapa`
--
CREATE DATABASE `etapa` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `etapa`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estacion`
--

CREATE TABLE IF NOT EXISTS `estacion` (
  `est_id` varchar(50) NOT NULL,
  `tip_id` int(11) NOT NULL,
  `est_latitud` float NOT NULL,
  `est_longitud` float NOT NULL,
  `est_provincia` varchar(50) DEFAULT NULL,
  `est_ciudad` varchar(50) DEFAULT NULL,
  `est_parroquia` varchar(50) DEFAULT NULL,
  `est_descripcion` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`est_id`,`tip_id`),
  KEY `tip_id` (`tip_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `estacion`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fenomeno`
--

CREATE TABLE IF NOT EXISTS `fenomeno` (
  `fen_id` varchar(50) NOT NULL,
  `fen_nombre` varchar(50) NOT NULL,
  `fen_descripcion` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`fen_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `fenomeno`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fenomeno_unidades`
--

CREATE TABLE IF NOT EXISTS `fenomeno_unidades` (
  `fen_id` varchar(50) NOT NULL,
  `uni_id` varchar(50) NOT NULL,
  `feu_maximo` float NOT NULL,
  `feu_minimo` float NOT NULL,
  `feu_alturasensor` float DEFAULT NULL,
  PRIMARY KEY (`fen_id`,`uni_id`),
  KEY `uni_id` (`uni_id`),
  KEY `fen_id` (`fen_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `fenomeno_unidades`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `observacion`
--

CREATE TABLE IF NOT EXISTS `observacion` (
  `obs_fecha` date NOT NULL,
  `est_id` varchar(50) NOT NULL,
  `tip_id` int(11) NOT NULL,
  `uni_id` varchar(50) NOT NULL,
  `fen_id` varchar(50) NOT NULL,
  `obs_valor` varchar(100) NOT NULL,
  PRIMARY KEY (`obs_fecha`,`est_id`,`tip_id`,`uni_id`,`fen_id`),
  KEY `est_id` (`est_id`,`tip_id`),
  KEY `fen_id` (`fen_id`,`uni_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `observacion`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_estacion`
--

CREATE TABLE IF NOT EXISTS `tipo_estacion` (
  `tip_id` int(11) NOT NULL AUTO_INCREMENT,
  `tip_nombre` varchar(50) NOT NULL,
  `tip_descripcion` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`tip_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcar la base de datos para la tabla `tipo_estacion`
--

INSERT INTO `tipo_estacion` (`tip_id`, `tip_nombre`, `tip_descripcion`) VALUES
(1, 'prueba', 'esta es una prueba');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `unidades`
--

CREATE TABLE IF NOT EXISTS `unidades` (
  `uni_id` varchar(50) NOT NULL,
  `uni_nombre` varchar(250) NOT NULL,
  `uni_descripcion` varchar(250) NOT NULL,
  `uni_tipo` varchar(50) NOT NULL,
  PRIMARY KEY (`uni_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `unidades`
--

