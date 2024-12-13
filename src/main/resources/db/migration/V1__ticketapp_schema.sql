CREATE SCHEMA IF NOT EXISTS `ticket_db` DEFAULT CHARACTER SET utf8 ;
USE `ticket_db` ;

CREATE TABLE `ticket_db`.`person` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    job_type VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    company VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE `ticket_db`.`ticket` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    signature VARCHAR(255) NOT NULL UNIQUE,
    offense_date DATE NOT NULL,
    offense_reason VARCHAR(255) NOT NULL,
    fine_amount DECIMAL(10, 2) NOT NULL CHECK (fine_amount > 0),
    currency VARCHAR(10) NOT NULL,
    administrative_fee DECIMAL(10, 2) NOT NULL DEFAULT 100.0,
    payment_due_date DATE NOT NULL,
    paid BOOLEAN NOT NULL DEFAULT FALSE,
    attachment LONGBLOB,
    person_id BIGINT,

    FOREIGN KEY (person_id) REFERENCES person(id) ON DELETE CASCADE
);
