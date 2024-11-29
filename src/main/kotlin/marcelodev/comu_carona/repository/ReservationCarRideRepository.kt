package marcelodev.comu_carona.repository

import marcelodev.comu_carona.models.ReservationCarRide
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationCarRideRepository: JpaRepository<ReservationCarRide, Long>