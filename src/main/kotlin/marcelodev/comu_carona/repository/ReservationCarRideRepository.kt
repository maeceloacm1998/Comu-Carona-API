package marcelodev.comu_carona.repository

import marcelodev.comu_carona.models.ReservationCarRide
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReservationCarRideRepository: JpaRepository<ReservationCarRide, Long> {

    @Query("SELECT r FROM ReservationCarRide r WHERE r.userId =:userId")
    fun findReservationByUserId(userId: String): List<ReservationCarRide>?
}