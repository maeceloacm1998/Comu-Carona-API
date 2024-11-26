package marcelodev.comu_carona.repository

import marcelodev.comu_carona.models.CarRide
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CarRideRepository : JpaRepository<CarRide, Long> {

    @Query("SELECT cr FROM CarRide cr WHERE cr.riderId =:userId ORDER BY cr.createdAt DESC LIMIT 1")
    fun findLastCarRideByUserId(userId: String): CarRide?

    @Query("SELECT cr FROM CarRide cr JOIN FETCH cr.user u WHERE cr.status = 'IN_PROGRESS' AND cr.riderId != :userId")
    fun findAllAvailableCarRidesExcludingUser(userId: String): List<CarRide>
}