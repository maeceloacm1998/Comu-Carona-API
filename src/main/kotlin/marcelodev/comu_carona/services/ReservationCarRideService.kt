package marcelodev.comu_carona.services

import marcelodev.comu_carona.models.ReservationCarRide
import marcelodev.comu_carona.repository.ReservationCarRideRepository
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class ReservationCarRideService(
    private val reservationCarRideRepository: ReservationCarRideRepository
) {
    private val logger = Logger.getLogger(ReservationCarRideService::class.java.name)

    fun createReservationCarRide(reservation: ReservationCarRide) {
        logger.info("Creating reservation car ride with data: $reservation")
        reservationCarRideRepository.save(reservation)
        logger.info("Reservation car ride created successfully")
    }
}