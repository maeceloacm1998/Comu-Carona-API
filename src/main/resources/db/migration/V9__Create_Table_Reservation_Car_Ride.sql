CREATE TABLE IF NOT EXISTS `car_ride_reservation` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `car_rider_uuid` VARCHAR(255) NOT NULL,
    `user_id` VARCHAR(255) NOT NULL,
    `username` VARCHAR(255),
    `birth_date` DATE,
    `phone_number` VARCHAR(20),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
