CREATE TABLE IF NOT EXISTS `car_ride` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` VARCHAR(255) DEFAULT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `car_model` VARCHAR(255) DEFAULT NULL,
    `car_color` VARCHAR(255) DEFAULT NULL,
    `car_plate` VARCHAR(255) DEFAULT NULL,
    `quantity_seats` INT DEFAULT NULL,
    `address` VARCHAR(255) DEFAULT NULL,
    `hour` VARCHAR(255) DEFAULT NULL,
    `status` VARCHAR(255) DEFAULT NULL,
    `is_two_passengers_behind` BOOLEAN DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;