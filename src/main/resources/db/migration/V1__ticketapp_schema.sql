CREATE TABLE ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    signature VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL UNIQUE,
    employee_type VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL,
    first_name VARCHAR(100) CHARACTER SET utf8mb4,
    last_name VARCHAR(100) CHARACTER SET utf8mb4,
    company VARCHAR(100) CHARACTER SET utf8mb4,
    phone_number VARCHAR(15) CHARACTER SET utf8mb4,
    violation_date DATE NOT NULL,
    reason VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL,
    amount FLOAT NOT NULL,
    currency VARCHAR(10) CHARACTER SET utf8mb4 NOT NULL,
    administrative_fee FLOAT DEFAULT 100.0 NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(50) CHARACTER SET utf8mb4 DEFAULT 'do zapłaty' NOT NULL,
    attachment_path VARCHAR(255) CHARACTER SET utf8mb4
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;