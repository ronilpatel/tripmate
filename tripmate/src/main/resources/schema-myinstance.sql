-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CSCI5308_16_DEVINT
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CSCI5308_16_DEVINT
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CSCI5308_16_DEVINT` DEFAULT CHARACTER SET utf8 ;
USE `CSCI5308_16_DEVINT` ;

-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `birthdate` DATE NULL,
  `gender` VARCHAR(16) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`VechicleCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`VechicleCategory` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`Vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`Vehicle` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  `no_of_seats` INT NULL,
  `registration_numb` VARCHAR(45) NULL,
  `is_ready_to_use` TINYINT NULL,
  `is_for_long_journey` TINYINT NULL,
  `VechicleCategory_id` INT NOT NULL,
  PRIMARY KEY (`id`, `VechicleCategory_id`),
  INDEX `fk_Vehicle_VechicleCategory1_idx` (`VechicleCategory_id` ASC) ,
  CONSTRAINT `fk_Vehicle_VechicleCategory1`
    FOREIGN KEY (`VechicleCategory_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`VechicleCategory` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`Post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`Post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `source_location` VARCHAR(45) NULL,
  `destination_location` VARCHAR(45) NULL,
  `start_ts` DATETIME NOT NULL,
  `end_ts` DATETIME NOT NULL,
  `min_age` INT NULL DEFAULT 0,
  `max_age` INT NULL,
  `is_hidden` TINYINT NOT NULL DEFAULT 0,
  `capacity` INT NULL,
  `created_by` INT NOT NULL,
  `description` TEXT NULL,
  `Vehicle_id` INT NOT NULL,
  PRIMARY KEY (`id`, `created_by`, `Vehicle_id`),
  INDEX `fk_Post_User_idx` (`created_by` ASC) ,
  INDEX `fk_Post_Vehicle1_idx` (`Vehicle_id` ASC) ,
  CONSTRAINT `fk_Post_User`
    FOREIGN KEY (`created_by`)
    REFERENCES `CSCI5308_16_DEVINT`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Post_Vehicle1`
    FOREIGN KEY (`Vehicle_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`Vehicle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`PostRequest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`PostRequest` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NULL DEFAULT 'PENDING',
  `Post_id` INT NOT NULL,
  `request_owner` INT NOT NULL,
  PRIMARY KEY (`id`, `Post_id`, `request_owner`),
  INDEX `fk_PostRequest_Post1_idx` (`Post_id` ASC) ,
  INDEX `fk_PostRequest_User1_idx` (`request_owner` ASC) ,
  CONSTRAINT `fk_PostRequest_Post1`
    FOREIGN KEY (`Post_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`Post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PostRequest_User1`
    FOREIGN KEY (`request_owner`)
    REFERENCES `CSCI5308_16_DEVINT`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`Hobby`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`Hobby` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`User_has_Hobby`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`User_has_Hobby` (
  `User_id` INT NOT NULL,
  `Hobby_id` INT NOT NULL,
  PRIMARY KEY (`User_id`, `Hobby_id`),
  INDEX `fk_User_has_Hobby_Hobby1_idx` (`Hobby_id` ASC) ,
  INDEX `fk_User_has_Hobby_User1_idx` (`User_id` ASC) ,
  CONSTRAINT `fk_User_has_Hobby_User1`
    FOREIGN KEY (`User_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Hobby_Hobby1`
    FOREIGN KEY (`Hobby_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`Hobby` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`Feedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`Feedback` (
  `id` INT NOT NULL,
  `postid` INT NULL,
  `userid` INT NULL,
  `feedback` VARCHAR(2000) NULL,
  `rating` DECIMAL(5) NULL,
  PRIMARY KEY (`id`),
  INDEX `postid_idx` (`postid` ASC) ,
  INDEX `userid_idx` (`userid` ASC) ,
  CONSTRAINT `postid`
    FOREIGN KEY (`postid`)
    REFERENCES `CSCI5308_16_DEVINT`.`Post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `userid`
    FOREIGN KEY (`userid`)
    REFERENCES `CSCI5308_16_DEVINT`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`TripVehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`TripVehicle` (
  `Post_id` INT NOT NULL,
  `Vehicle_id` INT NOT NULL,
  `rate_per_km` FLOAT NULL,
  `total_km` FLOAT NULL,
  PRIMARY KEY (`Post_id`, `Vehicle_id`),
  INDEX `fk_Post_has_Vehicle_Vehicle1_idx` (`Vehicle_id` ASC) ,
  INDEX `fk_Post_has_Vehicle_Post1_idx` (`Post_id` ASC) ,
  CONSTRAINT `fk_Post_has_Vehicle_Post1`
    FOREIGN KEY (`Post_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`Post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Post_has_Vehicle_Vehicle1`
    FOREIGN KEY (`Vehicle_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`Vehicle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CSCI5308_16_DEVINT`.`TripVehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CSCI5308_16_DEVINT`.`TripVehicle` (
  `Post_id` INT NOT NULL,
  `Vehicle_id` INT NOT NULL,
  `rate_per_km` FLOAT NULL,
  `total_km` FLOAT NULL,
  PRIMARY KEY (`Post_id`, `Vehicle_id`),
  INDEX `fk_Post_has_Vehicle_Vehicle1_idx` (`Vehicle_id` ASC) ,
  INDEX `fk_Post_has_Vehicle_Post1_idx` (`Post_id` ASC) ,
  CONSTRAINT `fk_Post_has_Vehicle_Post1`
    FOREIGN KEY (`Post_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`Post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Post_has_Vehicle_Vehicle1`
    FOREIGN KEY (`Vehicle_id`)
    REFERENCES `CSCI5308_16_DEVINT`.`Vehicle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;