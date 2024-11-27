package marcelodev.comu_carona.repository

import marcelodev.comu_carona.models.CarRide
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CarRideRepository : JpaRepository<CarRide, Long> {

    @Query("SELECT cr FROM CarRide cr WHERE cr.userId =:userId ORDER BY cr.createdAt DESC LIMIT 1")
    fun findLastCarRideByUserId(userId: String): CarRide?
}