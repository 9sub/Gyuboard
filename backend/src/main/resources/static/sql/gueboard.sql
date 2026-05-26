-- Gyuboard Database Schema
-- MySQL 기준

DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `guestable`;
DROP TABLE IF EXISTS `gueuser`;

CREATE TABLE `gueuser` (
    `user_id` INT NOT NULL AUTO_INCREMENT,
    `writer` VARCHAR(40) NOT NULL,
    `name` VARCHAR(40) NOT NULL,
    `password` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100),
    `regidate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_gueuser_writer` (`writer`)
);

CREATE TABLE `guestable` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `guecontents` VARCHAR(2000) NOT NULL,
    `writedate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `view_count` INT NOT NULL DEFAULT 0,

    PRIMARY KEY (`id`),

    CONSTRAINT `fk_guestable_user`
        FOREIGN KEY (`user_id`)
        REFERENCES `gueuser` (`user_id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE `comment` (
    `comment_id` INT NOT NULL AUTO_INCREMENT,
    `board_id` INT NOT NULL,
    `user_id` INT NOT NULL,
    `content` TEXT NOT NULL,
    `writedate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updatedate` DATETIME NULL,

    PRIMARY KEY (`comment_id`),

    CONSTRAINT `fk_comment_board`
        FOREIGN KEY (`board_id`)
        REFERENCES `guestable` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT `fk_comment_user`
        FOREIGN KEY (`user_id`)
        REFERENCES `gueuser` (`user_id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE INDEX `idx_guestable_user_id`
ON `guestable` (`user_id`);

CREATE INDEX `idx_guestable_writedate`
ON `guestable` (`writedate`);

CREATE INDEX `idx_guestable_view_count`
ON `guestable` (`view_count`);

CREATE INDEX `idx_comment_board_id`
ON `comment` (`board_id`);

CREATE INDEX `idx_comment_user_id`
ON `comment` (`user_id`);