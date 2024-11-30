package marcelodev.comu_carona.repository

import marcelodev.comu_carona.models.ReservationCarRide
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface ReservationCarRideRepository: JpaRepository<ReservationCarRide, Long> {

    @Query("SELECT r FROM ReservationCarRide r WHERE r.userId =:userId")
    fun findReservationByUserId(userId: String): List<ReservationCarRide>?

    @Modifying
    @Transactional
    @Query("DELETE FROM ReservationCarRide r WHERE r.carRiderUuid =:carRiderUUID AND r.userId =:userId")
    fun deleteReservationCarRideByIdAndUserId(carRiderUUID: String, userId: String)

    @Modifying
    @Transactional
    @Query("DELETE FROM ReservationCarRide r WHERE r.carRiderUuid =:carRiderUUID")
    fun deleteReservationCarRideById(carRiderUUID: String)
}