CREATE TABLE IF NOT EXISTS `reservation_car_ride` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `rider_id` VARCHAR(255) NOT NULL,
    `user_id` VARCHAR(255) NOT NULL,
    `username` VARCHAR(255),
    `birth_date` DATE,
    `phone_number` VARCHAR(20),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
