CREATE SCHEMA `internet_package_sale`;
USE `internet_package_sale`;
DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale`(
	`id` INT NOT NULL AUTO_INCREMENT,
    `firstLastname` VARCHAR(45) NOT NULL,
    `address` VARCHAR(45) NOT NULL,
    `speed` int(5) NOT NULL,
    `flow` varchar(5) NOT NULL,
    `contractDuration` int(1) NOT NULL,
    PRIMARY KEY (`id`));