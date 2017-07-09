CREATE TABLE `audit` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`dept_name` VARCHAR(255) NOT NULL,
	`loc_name` VARCHAR(255) NOT NULL,
	`reason_name` VARCHAR(255) NOT NULL,
	`session_id` INT(11) NOT NULL,
	`session_timestamp` DATETIME NOT NULL,
	`status` VARCHAR(15) NOT NULL,
	`user_name` VARCHAR(255) NOT NULL,
	`user_uid` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
)

CREATE TABLE `status_audit` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`status` VARCHAR(15) NOT NULL,
	`status_time` DATETIME NOT NULL,
	`user_uid` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`id`)
)