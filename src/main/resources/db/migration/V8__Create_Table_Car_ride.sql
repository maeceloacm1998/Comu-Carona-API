CREATE TABLE IF NOT EXISTS `car_ride` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `uuid` VARCHAR(36) DEFAULT NULL,
    `rider_id` VARCHAR(255) DEFAULT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `car_model` VARCHAR(255) DEFAULT NULL,
    `car_color` VARCHAR(255) DEFAULT NULL,
    `car_plate` VARCHAR(255) DEFAULT NULL,
    `quantity_seats` INT DEFAULT NULL,
    `waiting_address` VARCHAR(255) DEFAULT NULL,
    `destination_address` VARCHAR(255) DEFAULT NULL,
    `waiting_hour` VARCHAR(255) DEFAULT NULL,
    `destination_hour` VARCHAR(255) DEFAULT NULL,
    `status` VARCHAR(255) DEFAULT NULL,
    `is_two_passengers_behind` BOOLEAN DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;