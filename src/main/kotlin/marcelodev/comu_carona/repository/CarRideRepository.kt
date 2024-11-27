package marcelodev.comu_carona.repository

import marcelodev.comu_carona.models.CarRide
import org.springframework.data.jpa.repository.JpaRepository

interface CarRideRepository : JpaRepository<CarRide, Long>