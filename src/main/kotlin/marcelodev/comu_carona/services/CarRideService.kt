package marcelodev.comu_carona.services

import marcelodev.comu_carona.mapper.DozerMapper
import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.models.enums.CarRideState
import marcelodev.comu_carona.repository.CarRideRepository
import marcelodev.comu_carona.v1.CarRideVO
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class CarRideService(private val carRideRepository: CarRideRepository) {

    private val logger = Logger.getLogger(CarRideService::class.java.name)

    fun createCarRide(
        carRide: CarRideVO,
        userId: String
    ): CarRide {
        logger.info("Creating car ride with data: $carRide")


        val carRideEntity = DozerMapper.parseObject(carRide, CarRide::class.java)
        carRideEntity.apply {
            this.status = CarRideState.IN_PROGRESS.toString()
            this.userId = userId
        }

        return carRideRepository.save(carRideEntity)
    }
}