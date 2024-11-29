package marcelodev.comu_carona.services

import marcelodev.comu_carona.exceptions.ResourceNotFoundException
import marcelodev.comu_carona.mapper.CustomMapper
import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.models.ReservationCarRide
import marcelodev.comu_carona.models.User
import marcelodev.comu_carona.models.enums.CarRideState
import marcelodev.comu_carona.repository.CarRideRepository
import marcelodev.comu_carona.v1.rider.*
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class CarRideService(
    private val carRideRepository: CarRideRepository,
    private val carRideReservationService: CarRideReservationService,
    private val customMapper: CustomMapper,
) {

    private val logger = Logger.getLogger(CarRideService::class.java.name)

    /**
     * Create a car ride
     * @param carRide CarRideVO
     * @param userId String
     * @return CarRide
     */
    fun createCarRide(
        carRide: CarRideVO,
        userId: String
    ) {
        logger.info("Creating car ride with data: $carRide")

        val carRideEntity = customMapper.parseObject(carRide, CarRide::class.java)
        carRideEntity.apply {
            this.status = CarRideState.IN_PROGRESS.toString()
            this.riderId = userId
        }

        logger.info("Car ride created successfully, entity: $carRideEntity")
        carRideRepository.save(carRideEntity)

        logger.info("Car ride created successfully")
    }


    /**
     * Find last car ride by user id
     * @param userId String
     * @return CarRideVO
     * @throws ResourceNotFoundException
     */
    fun findLastCarRideByUserId(userId: String): CarRideVO? {
        logger.info("Finding last car ride by user id: $userId")
        val lastCarRide = carRideRepository.findLastCarRideByUserId(userId)
            ?: throw ResourceNotFoundException("No records found for this ID")

        logger.info("Last car ride found: $lastCarRide")
        return customMapper.parseObject(lastCarRide, CarRideVO::class.java)
    }

    /**
     * Find all available car rides
     * @param userId String
     * @return List<AvailableCarRidesVO>
     */
    fun availableCarRides(userId: String): List<AvailableCarRidesVO> {
        logger.info("Finding all available car rides")
        val availableCarRides = carRideRepository.findAllAvailableCarRidesExcludingUser(userId)

        if (availableCarRides.isEmpty()) {
            logger.info("No available car rides found")
            return emptyList()
        }

        logger.info("Available car rides found: $availableCarRides")
        val response = handleAvailableCarRidesResponse(availableCarRides)
        logger.info("Available car rides response: $response")
        return response
    }

    /**
     * Handle available car rides response
     * @param availableCarRides List<CarRide>
     * List of available car rides
     */
    private fun handleAvailableCarRidesResponse(availableCarRides: List<CarRide>): List<AvailableCarRidesVO> {
        return availableCarRides.map { carRide ->
            carRide.parseRideToAvailableCarRidesVO()
        }
    }

    /**
     * Find car ride by id
     * @param id String
     * @return CarRideVO
     * @throws ResourceNotFoundException
     */
    fun findCarRideById(id: String): DetailsCarRideVO {
        logger.info("Finding car ride by id: $id")
        val carRide = carRideRepository.findCarRideById(id)
            ?: throw ResourceNotFoundException("No records found for this ID")

        logger.info("Car ride found: $carRide")
        return carRide.parseRideToDetailsCarRideVO(customMapper)
    }

    /**
     * Reserve car ride
     * @param carRideId String
     * @param userId String
     * @return CarRide
     * @throws ResourceNotFoundException
     */
    fun reserveCarRide(carRideId: String, user: User) {
        logger.info("Reserving car ride with id: $carRideId")

        val reservation = ReservationCarRide(
            carRiderUuid = carRideId,
            userId = user.getUserId(),
            username = user.username,
            birthDate = user.getBirthDate(),
            phoneNumber = user.getPhoneNumber()
        )

        logger.info("Creating reservation car ride with data: $reservation")
        carRideReservationService.createReservationCarRide(reservation)

        logger.info("Reservation car ride created successfully")
    }
}