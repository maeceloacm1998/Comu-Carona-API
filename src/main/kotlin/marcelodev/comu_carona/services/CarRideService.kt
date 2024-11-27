package marcelodev.comu_carona.services

import marcelodev.comu_carona.exceptions.ResourceNotFoundException
import marcelodev.comu_carona.mapper.CustomMapper
import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.models.enums.CarRideState
import marcelodev.comu_carona.repository.CarRideRepository
import marcelodev.comu_carona.v1.CarRideVO
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class CarRideService(
    private val carRideRepository: CarRideRepository,
    private val customMapper: CustomMapper
) {

    private val logger = Logger.getLogger(CarRideService::class.java.name)

    fun createCarRide(
        carRide: CarRideVO,
        userId: String
    ): CarRide {
        logger.info("Creating car ride with data: $carRide")

        val carRideEntity = customMapper.parseObject(carRide, CarRide::class.java)
        carRideEntity.apply {
            this.status = CarRideState.IN_PROGRESS.toString()
            this.userId = userId
        }

        logger.info("Car ride created successfully, entity: $carRideEntity")
        return carRideRepository.save(carRideEntity)
    }

    fun findLastCarRideByUserId(userId: String): CarRideVO? {
        logger.info("Finding last car ride by user id: $userId")
        val lastCarRide = carRideRepository.findLastCarRideByUserId(userId)
            ?: throw ResourceNotFoundException("No records found for this ID")

        logger.info("Last car ride found: $lastCarRide")
        return customMapper.parseObject(lastCarRide, CarRideVO::class.java)
    }
}