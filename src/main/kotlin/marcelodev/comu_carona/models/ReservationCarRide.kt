package marcelodev.comu_carona.models

import jakarta.persistence.*

@Entity
@Table(name = "car_ride_reservation")
data class ReservationCarRide(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "car_rider_uuid", nullable = false)
    var carRiderUuid: String,

    @Column(name = "user_id", nullable = false)
    var userId: String,

    var username: String = "",
    var birthDate: String = "",
    var phoneNumber: String = ""
)