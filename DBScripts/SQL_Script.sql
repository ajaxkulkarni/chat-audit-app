CREATE TABLE `audit` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`dept_name` VARCHAR(255),
	`loc_name` VARCHAR(255),
	`reason_name` VARCHAR(255),
	`session_id` INT(11) NOT NULL,
	`session_timestamp` DATETIME NOT NULL,
	`status` VARCHAR(15),
	`user_name` VARCHAR(255),
	`user_uid` VARCHAR(255),
	PRIMARY KEY (`id`)
)

CREATE TABLE `status_audit` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`status` VARCHAR(15) NOT NULL,
	`status_time` DATETIME NOT NULL,
	`user_uid` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
)