package marcelodev.comu_carona.models

import jakarta.persistence.*

@Entity
@Table(name = "reservation_car_ride")
data class ReservationCarRide(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "rider_id", nullable = false)
    var riderId: String,

    @Column(name = "user_id", nullable = false)
    var userId: String,

    var username: String = "",
    var birthDate: String = "",
    var phoneNumber: String = ""
)