CREATE TABLE IF NOT EXISTS `car_ride_status` (
                                                 `car_ride_id` BIGINT,
                                                 `status` VARCHAR(255),
    FOREIGN KEY (`car_ride_id`) REFERENCES `car_ride`(`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;