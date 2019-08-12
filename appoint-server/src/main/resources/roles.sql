CREATE DATABASE `testdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `registered_users` (
  `USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(50) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `NAME` varchar(50) NOT NULL,
  `PHONE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `PHONE` (`PHONE`)
) ;

CREATE TABLE `roles` (
  `ROLE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`),
  UNIQUE KEY `ROLE_NAME` (`ROLE_NAME`)
) ;

CREATE TABLE `time_off` (
  `time_off_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL,
  `start_time` time NOT NULL,
  `end_date` date NOT NULL,
  `end_time` time NOT NULL,
  `full_day` tinyint(4) DEFAULT '0',
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`time_off_id`)
) ;


CREATE TABLE `user_role` (
  `USER_ID` bigint(20) NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`,`ROLE_ID`)
) ;


CREATE TABLE `patients` (
  `PATIENT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `AGE` int(3) NOT NULL,
  `GENDER` varchar(1) NOT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `USER_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PATIENT_ID`),
  KEY `FK_USER_PATIENT` (`USER_ID`),
  CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `registered_users` (`USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ;


CREATE TABLE `appointment` (
  `APPOINTMENT_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DATE` date DEFAULT NULL,
  `TIME` time DEFAULT NULL,
  `PATIENT_ID` bigint(20) DEFAULT NULL,
  `PHONE` varchar(10) DEFAULT NULL,
  `DESCRIPTION` varchar(200) DEFAULT NULL,
  `STATUS` varchar(30) NOT NULL,
  PRIMARY KEY (`APPOINTMENT_ID`),
  KEY `FK_PATIENT_APPOINTMENT` (`PATIENT_ID`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`PATIENT_ID`) REFERENCES `patients` (`PATIENT_ID`)
) ;

CREATE TABLE `patient_file` (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `patient_id` bigint(20) NOT NULL,
  `occupation` varchar(45) DEFAULT NULL,
  `p_c` varchar(10000) DEFAULT NULL,
  `married_since` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `patient_patient_file_fk_idx` (`patient_id`),
  CONSTRAINT `patient_patient_file_fk` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`PATIENT_ID`)
) ;

CREATE TABLE `prescription` (
  `pres_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prescription` varchar(1000) DEFAULT NULL,
  `patient_id` bigint(20) DEFAULT NULL,
  `Datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`pres_id`),
  KEY `patient_prescription_idx` (`patient_id`),
  CONSTRAINT `patient_prescription` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`PATIENT_ID`)
) ;

----------------------------------------------------------------------------------------------------------------------------------------------
INSERT INTO roles(role_name) VALUES('ROLE_USER');
INSERT INTO roles(role_name) VALUES('ROLE_PM');
INSERT INTO roles(role_name) VALUES('ROLE_ADMIN');