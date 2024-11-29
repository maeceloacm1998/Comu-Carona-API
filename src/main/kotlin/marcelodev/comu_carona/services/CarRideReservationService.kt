package marcelodev.comu_carona.services

import marcelodev.comu_carona.exceptions.ResourceNotFoundException
import marcelodev.comu_carona.mapper.CustomMapper
import marcelodev.comu_carona.models.CarRide
import marcelodev.comu_carona.models.ReservationCarRide
import marcelodev.comu_carona.repository.CarRideRepository
import marcelodev.comu_carona.repository.ReservationCarRideRepository
import marcelodev.comu_carona.v1.rider.CarRideMyReservationVO
import marcelodev.comu_carona.v1.rider.parseRideToCarRideMyReservationVO
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class CarRideReservationService(
    private val reservationCarRideRepository: ReservationCarRideRepository,
    private val carRideRepository: CarRideRepository,
    private val customMapper: CustomMapper
) {
    private val logger = Logger.getLogger(CarRideReservationService::class.java.name)

    /**
     * Create a reservation car ride
     * @param reservation ReservationCarRide
     * @return void
     */
    fun createReservationCarRide(reservation: ReservationCarRide) {
        logger.info("Creating reservation car ride with data: $reservation")
        reservationCarRideRepository.save(reservation)
        logger.info("Reservation car ride created successfully")
    }

    /**
     * Find my reservations and my car rides
     * @param userId String
     * @return List<CarRideMyReservationVO>
     */
    fun findMyReservations(userId: String): List<CarRideMyReservationVO> {
        logger.info("Finding reservations")
        val myCarRides: List<CarRide> = carRideRepository.findCarRideByRiderId(userId) ?: emptyList()
        val reservations: List<ReservationCarRide> = findReservationByUserId(userId)

        logger.info("Reservations found: $reservations")
        val myReservationList = handleMyReservationList(
            userId = userId,
            myCarRide = myCarRides,
            reservations = reservations
        )

        logger.info("Reservations handled successfully: $myReservationList")
        return myReservationList
    }

    /**
     * Handle my reservation list
     * @param userId String
     * @param reservations List<ReservationCarRide>
     * @return List<CarRideMyReservationVO>
     */
    private fun handleMyReservationList(
        userId: String,
        myCarRide: List<CarRide>,
        reservations: List<ReservationCarRide>
    ): List<CarRideMyReservationVO> {
        logger.info("Handling reservations to car rides")
        val myReservationList: MutableList<CarRideMyReservationVO> = handleCarRideListToMyReservationVO(
            userId = userId,
            carRideList = myCarRide
        ).toMutableList()

        reservations.map { reservation ->
            val carRide: CarRide = carRideRepository.findCarRideById(reservation.carRiderUuid)
                ?: throw ResourceNotFoundException("No records found car ride for this ID: ${reservation.carRiderUuid}")
            val myReservationVO = carRide.parseRideToCarRideMyReservationVO(
                userId = userId,
                customMapper = customMapper
            )
            myReservationList.add(myReservationVO)
        }

        logger.info("Reservations handled successfully")
        return myReservationList
    }

    /**
     * Handle car ride list to my reservation VO
     * @param userId String
     * @param carRideList List<CarRide>
     * @return List<CarRideMyReservationVO>
     */
    private fun handleCarRideListToMyReservationVO(
        userId: String,
        carRideList: List<CarRide>
    ): List<CarRideMyReservationVO> {
        return carRideList.map { carRide ->
            carRide.parseRideToCarRideMyReservationVO(
                userId = userId,
                customMapper = customMapper
            )
        }
    }

    /**
     * Find reservation by user id
     * @param userId String
     * @return List<ReservationCarRide>
     */
    private fun findReservationByUserId(userId: String): List<ReservationCarRide> {
        logger.info("Finding reservation by user id: $userId")
        val reservations = reservationCarRideRepository.findReservationByUserId(userId) ?: emptyList()
        logger.info("Reservations found: $reservations")
        return reservations
    }
}