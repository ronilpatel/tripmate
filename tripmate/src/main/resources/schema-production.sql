SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CSCI5308_16_DEVINT
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CSCI5308_16_DEVINT
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CSCI5308_16_PRODUCTION` DEFAULT CHARACTER SET utf8 ;
USE `CSCI5308_16_PRODUCTION` ;

-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `User` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `first_name` VARCHAR(45) NOT NULL,
                                      `last_name` VARCHAR(45) NULL,
                                      `email` VARCHAR(45) NOT NULL,
                                      `password` VARCHAR(200) NOT NULL,
                                      `birthdate` DATE NULL,
                                      `gender` VARCHAR(16) NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE INDEX `email_UNIQUE` (`email` ASC)  )
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Post` (
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
                                      PRIMARY KEY (`id`, `created_by`),
                                      INDEX `fk_Post_User_idx` (`created_by` ASC)  ,
                                      CONSTRAINT `fk_Post_User`
                                          FOREIGN KEY (`created_by`)
                                              REFERENCES `User` (`id`)
                                              ON DELETE CASCADE
                                              ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PostRequest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `PostRequest` (
                                             `id` INT NOT NULL AUTO_INCREMENT,
                                             `status` VARCHAR(45) NULL DEFAULT 'pending',
                                             `post_id` INT NOT NULL,
                                             `requestOwner` INT NOT NULL,
                                             PRIMARY KEY (`id`, `post_id`, `requestOwner`),
                                             INDEX `fk_PostRequest_Post1_idx` (`post_id` ASC)  ,
                                             INDEX `fk_PostRequest_User1_idx` (`requestOwner` ASC)  ,
                                             CONSTRAINT `fk_PostRequest_Post1`
                                                 FOREIGN KEY (`post_id`)
                                                     REFERENCES `Post` (`id`)
                                                     ON DELETE CASCADE
                                                     ON UPDATE NO ACTION,
                                             CONSTRAINT `fk_PostRequest_User1`
                                                 FOREIGN KEY (`requestOwner`)
                                                     REFERENCES `User` (`id`)
                                                     ON DELETE CASCADE
                                                     ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VechicleCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VechicleCategory` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `name` VARCHAR(45) NULL,
                                                  PRIMARY KEY (`id`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Vehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Vehicle` (
                                         `id` INT NOT NULL,
                                         `name` VARCHAR(45) NULL,
                                         `no_of_seats` INT NULL,
                                         `registration_numb` VARCHAR(45) NULL,
                                         `is_available` TINYINT NULL,
                                         `is_for_long_journey` TINYINT NULL,
                                         `vechicle_category_id` INT NOT NULL,
                                         `rate_per_km` FLOAT NULL DEFAULT 0.0,
                                         `description` TEXT NULL,
                                         PRIMARY KEY (`id`, `vechicle_category_id`),
                                         INDEX `fk_Vehicle_VechicleCategory1_idx` (`vechicle_category_id` ASC)  ,
                                         CONSTRAINT `fk_Vehicle_VechicleCategory1`
                                             FOREIGN KEY (`vechicle_category_id`)
                                                 REFERENCES `VechicleCategory` (`id`)
                                                 ON DELETE CASCADE
                                                 ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Feedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Feedback` (
                                          `id` INT NOT NULL,
                                          `post_id` INT NULL,
                                          `user_id` INT NULL,
                                          `feedback` VARCHAR(2000) NULL,
                                          `rating` DECIMAL(5) NULL,
                                          PRIMARY KEY (`id`),
                                          INDEX `postid_idx` (`post_id` ASC)  ,
                                          INDEX `userid_idx` (`user_id` ASC)  ,
                                          CONSTRAINT `postid`
                                              FOREIGN KEY (`post_id`)
                                                  REFERENCES `Post` (`id`)
                                                  ON DELETE CASCADE
                                                  ON UPDATE NO ACTION,
                                          CONSTRAINT `userid`
                                              FOREIGN KEY (`user_id`)
                                                  REFERENCES `User` (`id`)
                                                  ON DELETE CASCADE
                                                  ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TripVehicle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TripVehicle` (
                                             `total_km` FLOAT NULL DEFAULT 0,
                                             `rate_per_km` FLOAT NULL)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VehicleBooking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VehicleBooking` (
                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                `post_id` INT NOT NULL,
                                                `vehicle_id` INT NOT NULL,
                                                `total_km` FLOAT NULL,
                                                `bk_start_date` DATE NULL,
                                                `bk_end_date` DATE NULL,
                                                `has_paid` TINYINT NULL DEFAULT 0,
                                                `created_on` DATE NULL,
                                                PRIMARY KEY (`id`, `post_id`, `vehicle_id`),
                                                INDEX `fk_Post_has_Vehicle_Vehicle1_idx` (`vehicle_id` ASC)  ,
                                                INDEX `fk_Post_has_Vehicle_Post1_idx` (`post_id` ASC)  ,
                                                CONSTRAINT `fk_Post_has_Vehicle_Post1`
                                                    FOREIGN KEY (`post_id`)
                                                        REFERENCES `Post` (`id`)
                                                        ON DELETE CASCADE
                                                        ON UPDATE NO ACTION,
                                                CONSTRAINT `fk_Post_has_Vehicle_Vehicle1`
                                                    FOREIGN KEY (`vehicle_id`)
                                                        REFERENCES `Vehicle` (`id`)
                                                        ON DELETE CASCADE
                                                        ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `VehicleBookingPayment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `VehicleBookingPayment` (
                                                       `payment_id` INT NOT NULL AUTO_INCREMENT,
                                                       `amount` FLOAT NULL DEFAULT 0.0,
                                                       `vehicle_booking_id` INT NOT NULL,
                                                       `created_on` DATE NULL,
                                                       PRIMARY KEY (`payment_id`, `vehicle_booking_id`),
                                                       INDEX `fk_VehicleBookingPayment_VehicleBooking1_idx` (`vehicle_booking_id` ASC)  ,
                                                       CONSTRAINT `fk_VehicleBookingPayment_VehicleBooking1`
                                                           FOREIGN KEY (`vehicle_booking_id`)
                                                               REFERENCES `VehicleBooking` (`id`)
                                                               ON DELETE CASCADE
                                                               ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;